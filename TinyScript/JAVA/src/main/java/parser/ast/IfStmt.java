package parser.ast;


import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class IfStmt extends Stmt {

    public IfStmt(AstNode parent) {
        super(parent, AstNodeTypes.IF_STMT, "if");
    }

    public static AstNode parse(AstNode parent, PeekTokenIterator it) throws ParseException {
        return parseIF(parent, it);
    }

    public static AstNode parseIF(AstNode parent, PeekTokenIterator it) throws ParseException {
        Token lexeme = it.nextMatch("if");
        it.nextMatch("(");
        IfStmt ifStmt = new IfStmt(parent);
        ifStmt.setLexeme(lexeme);
        AstNode expr = Expr.parse(parent, it);
        ifStmt.addChild(expr);
        it.nextMatch(")");
        AstNode block = Block.parse(parent, it);
        ifStmt.addChild(block);

        AstNode tail = parseTail(parent, it);
        if (tail != null) {
            ifStmt.addChild(tail);
        }
        return ifStmt;
    }

    // Tail -> else {Block} | else IFStmt | ε
    public static AstNode parseTail(AstNode parent, PeekTokenIterator it) throws ParseException {
        if (!it.hasNext() || !it.peek().getValue().equals("else"))
            return null;
        it.nextMatch("else");
        Token lookahead = it.peek();

        if (lookahead.getValue().equals("{")) {
            return Block.parse(parent, it);
        } else if (lookahead.getValue().equals("if")) {
            return parseIF(parent, it);
        } else
            return null;
    }


}
