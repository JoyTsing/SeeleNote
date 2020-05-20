package parser.ast;


import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class AssignStmt extends Stmt {

    public AssignStmt(AstNode parent) {
        super(parent, AstNodeTypes.ASSIGN_STMT, "assign");
    }

    public static AstNode parse(AstNode parent,PeekTokenIterator it) throws ParseException {
        var stmt = new AssignStmt(parent);
        var tkn = it.peek();
        var factor = Factor.parse(parent,it);
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
