package parser.ast;


import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class AssignStmt extends Stmt {

    public AssignStmt() {
        super(AstNodeTypes.ASSIGN_STMT, "assign");
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        var stmt = new AssignStmt();
        var tkn = it.peek();
        var factor = Factor.parse(it);
        if(factor == null) {
            throw new ParseException(tkn);
        }
        stmt.addChild(factor);
        var lexeme = it.nextMatch("=");
        var expr = Expr.parse(it);
        stmt.addChild(expr);
        stmt.setLexeme(lexeme);
        return stmt;
    }
}
