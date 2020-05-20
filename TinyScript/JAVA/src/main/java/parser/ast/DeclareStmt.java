package parser.ast;

import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class DeclareStmt extends Stmt {

    public DeclareStmt() {
        super(AstNodeTypes.DECLARE_STMT, "declare");
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        var stmt = new DeclareStmt();
        it.nextMatch("var");
        var tkn = it.peek();
        var factor = Factor.parse(it);
        if (factor == null)
            throw new ParseException(tkn);
        stmt.addChild(factor);
        Token lexeme = it.nextMatch("=");
        AstNode expr = Expr.parse(it);
        stmt.addChild(expr);
        stmt.setLexeme(lexeme);
        return stmt;
    }
}
