package translator;


import lexer.Token;
import lexer.TokenType;
import org.apache.commons.lang3.NotImplementedException;
import parser.ast.*;
import parser.util.ParseException;
import translator.symbol.Symbol;
import translator.symbol.SymbolTable;

import java.util.ArrayList;

public class Translator {
    public TAProgram translate(AstNode astNode) throws ParseException {
        var program = new TAProgram();
        var symbolTable = new SymbolTable();
        for (var child :
                astNode.getChildren()) {
            translateStmt(program, child, symbolTable);
        }
        program.setStaticSymbols(symbolTable);
        return program;
    }


    public void translateStmt(TAProgram program, AstNode node, SymbolTable symbolTable) throws ParseException {
        switch (node.getType()) {
            case BLOCK:
                translateBlock(program, (Block) node, symbolTable);
                return;
            case IF_STMT:
                translateIfStmt(program, (IfStmt) node, symbolTable);
                return;
            case ASSIGN_STMT:
                translateAssignStmt(program, node, symbolTable);
                return;
            case DECLARE_STMT:
                translateDeclareStmt(program, node, symbolTable);
                return;
            case FUNCTION_DECLARE_STMT:
                translateFunctionDeclareStmt(program, node, symbolTable);
                return;
            case RETURN_STMT:
                translateReturnStmt(program, node, symbolTable);
                return;
            case CALL_EXPR:
                translateCallExpr(program, node, symbolTable);
                return;
        }
        throw new NotImplementedException("Translator not impl. for " + node.getType());
    }

    private Symbol translateCallExpr(TAProgram program, AstNode node, SymbolTable symbolTable) throws ParseException {

        //foo()
        var factor = node.getChildren(0);
        //foo()-> symbol (foo) L0
        //a=foo()
        //func foo(){return 100}
        var list = new ArrayList<TAInstruction>();

        for (int i = 1; i < node.getChildren().size(); i++) {
            var expr = node.getChildren().get(i);
            var addr = translateExpr(program, expr, symbolTable);
            list.add(new TAInstruction(TAInstructionType.PARAM, null, null, addr, i - 1));
        }

        for (var instruction : list) {
            instruction.setArg2(symbolTable.localSize() + (int) instruction.getArg2() + 2);
            program.add(instruction);
        }

        var returnValue = symbolTable.createVariable();
        var funcAddr = symbolTable.cloneFromSymbolTree(factor.getLexeme(), 0);
        if (funcAddr == null) {
            throw new ParseException("function " + factor.getLexeme().getValue() + " not found");
        }
        //活动记录
        program.add(new TAInstruction(TAInstructionType.SP, null, null,
                -symbolTable.localSize(), null));
        program.add(new TAInstruction(TAInstructionType.CALL, null, null, funcAddr, null));
        //活动记录
        program.add(new TAInstruction(TAInstructionType.SP, null, null,
                symbolTable.localSize(), null));
        return returnValue;
    }

    public void translateReturnStmt(TAProgram program, AstNode node, SymbolTable symbolTable) throws ParseException {
        Symbol resultValue = null;
        if (node.getChildren(0) != null) {
            resultValue = translateExpr(program, node.getChildren(0), symbolTable);
        }
        program.add(new TAInstruction(TAInstructionType.RETURN, null, null, resultValue, null));
    }

    public void translateFunctionDeclareStmt(TAProgram program, AstNode node, SymbolTable parent) throws ParseException {
        var label = program.addLabel();
        var func = (FunctionDeclareStmt) node;

        var symbolTable = new SymbolTable();
        program.add(new TAInstruction(TAInstructionType.FUNC_BEGIN, null, null, null, null));
        symbolTable.createVariable(); // 返回地址

        symbolTable.createLabel((String) label.getArg1(), node.getLexeme()); //
        label.setArg2(node.getLexeme().getValue());//函数反向关联

        parent.addChild(symbolTable);
        parent.createLabel((String)label.getArg1(), node.getLexeme());

        for (var arg : func.getArgs().getChildren()) {//入参都写到符号表中
            symbolTable.createSymbolByLexeme(arg.getLexeme());
        }

        for (var child : func.getBlock().getChildren()) {//函数下的语句block
            translateStmt(program, child, symbolTable);
        }


    }


    public void translateIfStmt(TAProgram program, IfStmt node, SymbolTable symbolTable) throws ParseException {
        var expr = node.getExpr();
        var exprAddr = translateExpr(program, expr, symbolTable);
        var ifOpCode = new TAInstruction(TAInstructionType.IF, null, null, exprAddr, null);
        program.add(ifOpCode);

        translateBlock(program, (Block) node.getBlock(), symbolTable);

        TAInstruction gotoInstruction = null;
        if (node.getChildren(2) != null) {
            gotoInstruction = new TAInstruction(TAInstructionType.GOTO, null, null, null, null);
            program.add(gotoInstruction);
            var labelEndIf = program.addLabel();
            ifOpCode.setArg2(labelEndIf.getArg1());
        }

        if (node.getElseBlock() != null) {
            translateBlock(program, (Block) node.getElseBlock(), symbolTable);
        } else if (node.getElseIfStmt() != null) {
            translateIfStmt(program, (IfStmt) node.getElseIfStmt(), symbolTable);
        }

        var labelEnd = program.addLabel();
        if (node.getChildren(2) == null) {
            ifOpCode.setArg2(labelEnd.getArg1());
        } else {
            gotoInstruction.setArg1(labelEnd.getArg1());
        }
    }


    private void translateBlock(TAProgram program, Block node, SymbolTable parent) throws ParseException {
        var symbolTable = new SymbolTable();


        /**
         * 每个Block增加一个作用域链
         */
        var parentOffset = symbolTable.createVariable();
        parentOffset.setLexeme(new Token(TokenType.INTEGER, parent.localSize() + ""));//他的词法单元的具体意义

        //pushRecord
        // var pushRecord=new TAInstruction(TAInstructionType.SP,null,null,null,null);
        // program.add(pushRecord);

        parent.addChild(symbolTable);
        for (var stmt : node.getChildren()) {
            translateStmt(program, stmt, symbolTable);
        }

        // var popRecord=new TAInstruction(TAInstructionType.SP,null,null,null,null);
        // program.add(popRecord);

        // pushRecord.setArg1(-parent.localSize());
        //popRecord.setArg1(parent.localSize());
    }

    public void translateDeclareStmt(TAProgram program, AstNode node, SymbolTable symbolTable) throws ParseException {
        var lexeme = node.getChildren(0).getLexeme();
        if (symbolTable.exists(lexeme)) {
            throw new ParseException("Syntax Error,Identifier " + lexeme.getValue() + " is already defined");
        }

        var assigned = symbolTable.createSymbolByLexeme(lexeme);
        var expr = node.getChildren(1);
        var addr = translateExpr(program, expr, symbolTable);
        program.add(new TAInstruction(TAInstructionType.ASSIGN, assigned, "=", addr, null));
    }

    public void translateAssignStmt(TAProgram program, AstNode node, SymbolTable symbolTable) throws ParseException {
        //var a= expr
        /*
            2*3+1
            p0=2*3
            p1=p0+1
         */
        var assigned = symbolTable.createSymbolByLexeme(node.getChildren(0).getLexeme());
        var expr = node.getChildren(1);
        var addr = translateExpr(program, expr, symbolTable);
        program.add(new TAInstruction(TAInstructionType.ASSIGN, assigned, "=", addr, null));
    }

    public Symbol translateExpr(TAProgram program, AstNode node, SymbolTable symbolTable) throws ParseException {
        if (node.isValueType()) {
            var addr = symbolTable.createSymbolByLexeme(node.getLexeme());
            node.setProp("addr", addr);
            return addr;
        } else if (node.getType() == AstNodeTypes.CALL_EXPR) {
            var addr = translateCallExpr(program,node,symbolTable);
            node.setProp("addr", addr);
            return addr;
        }else if(node instanceof Expr) {

            for (var child : node.getChildren()) {
                translateExpr(program, child, symbolTable);
            }

            if (node.getProp("addr") == null) {
                node.setProp("addr", symbolTable.createVariable());
            }
            var instruction = new TAInstruction(
                    TAInstructionType.ASSIGN,
                    (Symbol) (node.getProp("addr")),
                    node.getLexeme().getValue(),
                    (Symbol) (node.getChildren(0).getProp("addr")),
                    (Symbol) (node.getChildren(1).getProp("addr"))
            );
            program.add(instruction);
            return instruction.getResult();
        }
        throw new NotImplementedException("Unexpected node type :" + node.getType());
    }
}
