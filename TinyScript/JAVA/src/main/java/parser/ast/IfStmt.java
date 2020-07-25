package parser.ast;


import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class IfStmt extends Stmt {

    public IfStmt() {
        super(AstNodeTypes.IF_STMT, "if");
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        return parseIF(it);
    }

    public static AstNode parseIF(PeekTokenIterator it) throws ParseException {
        var  lexeme = it.nextMatch("if");
        it.nextMatch("(");
        var  ifStmt = new IfStmt();
        ifStmt.setLexeme(lexeme);
        var  expr = Expr.parse(it);
        ifStmt.addChild(expr);
        it.nextMatch(")");
        var  block = Block.parse(it);
        ifStmt.addChild(block);

        var tail = parseTail(it);
        if (tail != null) {
            ifStmt.addChild(tail);
        }
        return ifStmt;
    }

    // Tail -> else {Block} | else IFStmt | ε
    public static AstNode parseTail(PeekTokenIterator it) throws ParseException {
        if (!it.hasNext() || !it.peek().getValue().equals("else"))
            return null;
        it.nextMatch("else");
        Token lookahead = it.peek();

        if (lookahead.getValue().equals("{")) {
            return Block.parse(it);
        } else if (lookahead.getValue().equals("if")) {
            return parseIF(it);
        } else
            return null;
    }

    public AstNode getExpr() {
        return this.getChildren(0);
    }

    public AstNode getBlock(){
        return this.getChildren(1);
    }

    public AstNode getElseBlock(){

        var block = this.getChildren(2);
        if(block instanceof Block) {
            return block;
        }
        return null;
    }

    public AstNode getElseIfStmt(){
        var ifStmt = this.getChildren(2);
        if(ifStmt instanceof IfStmt) {
            return ifStmt;
        }
        return null;
    }

}
