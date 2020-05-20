package parser.ast;

import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class DeclareStmt extends Stmt {

    public DeclareStmt(AstNode parent) {
        super(parent, AstNodeTypes.DECLARE_STMT, "declare");
    }

    public static AstNode parse(AstNode parent, PeekTokenIterator it) throws ParseException {
        var stmt = new DeclareStmt(parent);
        it.nextMatch("var");
        var tkn = it.peek();
        var factor = Factor.parse(parent, it);
        if (factor == null)
            throw new ParseException(tkn);
        stmt.addChild(factor);
        Token lexeme = it.nextMatch("=");
        AstNode expr = Expr.parse(parent, it);
        stmt.addChild(expr);
        stmt.setLexeme(lexeme);
        return stmt;
    }
}
