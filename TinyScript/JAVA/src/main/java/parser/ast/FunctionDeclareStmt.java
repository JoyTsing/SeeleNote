package parser.ast;


import lexer.Token;
import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionDeclareStmt extends Stmt {

    public FunctionDeclareStmt(AstNode parent) {
        super(parent, AstNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }

    public static AstNode parse(AstNode parent, PeekTokenIterator it) throws ParseException {
        it.nextMatch("func");

        //func add() int {}
        FunctionDeclareStmt func = new FunctionDeclareStmt(parent);
        Token lexeme = it.peek();//函数变量名
        Variable functionVariable = (Variable) Factor.parse(parent, it);
        func.setLexeme(lexeme);
        func.addChild(functionVariable);
        it.nextMatch("(");
        AstNode args = FunctionArgs.parse(parent, it);
        it.nextMatch(")");

        func.addChild(args);
        //BUG点 忘记更新keyword了
        Token keyword = it.nextMatch(TokenType.KEYWORD);
        if (!keyword.isType())
            throw new ParseException(keyword);

        functionVariable.setTypeLexeme(keyword);
        AstNode block = Block.parse(parent, it);
        func.addChild(block);
        return func;
    }

    public AstNode getArgs() {
        return this.getChildren(1);
    }

    public Variable getFunctionVariable() {
        return (Variable) this.getChildren(0);
    }

    public String getFuncType() {
        return this.getFunctionVariable().getTypeLexeme().getValue();
    }

    public Block getBlock() {
        return (Block) this.getChildren(2);
    }
}
