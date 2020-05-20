package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class CallExpr extends Expr{
    public CallExpr(AstNode parent) {
        super(parent);
        this.label = "call";
        this.type = AstNodeTypes.CALL_EXPR;

    }

    public static AstNode parse(AstNode parent,AstNode factor, PeekTokenIterator it) throws ParseException {
        var expr = new CallExpr(parent);
        expr.addChild(factor);

        it.nextMatch("(");
        AstNode p = null;
        while((p = Expr.parse(it)) != null) {
            expr.addChild(p);
            if(!it.peek().getValue().equals(")")) {
                it.nextMatch(",");
            }
        }
        it.nextMatch(")");
        return expr;
    }
}
