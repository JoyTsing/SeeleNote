package parser.ast;

import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class CallExpr extends Expr {
    public CallExpr() {
        super();
        this.label = "call";
        this.type = AstNodeTypes.CALL_EXPR;

    }

    public static AstNode parse(AstNode factor, PeekTokenIterator it) throws ParseException {
        var expr = new CallExpr();
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