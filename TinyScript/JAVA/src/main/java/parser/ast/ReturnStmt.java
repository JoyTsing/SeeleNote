package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class ReturnStmt extends Stmt {
    public ReturnStmt() {
        super(AstNodeTypes.RETURN_STMT, "return");
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        var lexeme = it.nextMatch("return");
        var expr = Expr.parse(it);

        var stmt = new ReturnStmt();
        stmt.setLexeme(lexeme);
        if(expr != null) {
            stmt.addChild(expr);
        }
        return stmt;
    }


}