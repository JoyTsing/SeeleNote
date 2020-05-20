package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class ReturnStmt extends Stmt{

    public ReturnStmt(AstNode parent) {
        super(parent,AstNodeTypes.RETURN_STMT, "return");
    }

    public static AstNode parse(AstNode parent,PeekTokenIterator it) throws ParseException {
        var lexeme = it.nextMatch("return");
        var expr = Expr.parse(it);

        var stmt = new ReturnStmt(parent);
        stmt.setLexeme(lexeme);
        if(expr != null) {
            stmt.addChild(expr);
        }
        return stmt;
    }
}
