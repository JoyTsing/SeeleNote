package parser.ast;

import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionDeclareStmt extends Stmt {


    public FunctionDeclareStmt() {
        super( AstNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        it.nextMatch("func");

        // func add() int {}
        var func = new FunctionDeclareStmt();
        var lexeme = it.peek();
        var functionVariable = (Variable)Factor.parse( it);
        func.setLexeme(lexeme);
        func.addChild(functionVariable);
        it.nextMatch("(");
        var args = FunctionArgs.parse( it);
        it.nextMatch(")");
        func.addChild(args);
        var keyword = it.nextMatch(TokenType.KEYWORD);
        if(!keyword.isType()) {
            throw new ParseException(keyword);
        }

        functionVariable.setTypeLexeme(keyword);
        var block = Block.parse(it);
        func.addChild(block);
        return func;
    }

    public AstNode getArgs(){
        return this.getChildren(1);
    }

    public Variable getFunctionVariable() {
        return (Variable)this.getChildren(0);
    }

    public String getFuncType(){
        return this.getFunctionVariable().getTypeLexeme().getValue();
    }

    public Block getBlock(){
        return (Block)this.getChildren(2);
    }


}