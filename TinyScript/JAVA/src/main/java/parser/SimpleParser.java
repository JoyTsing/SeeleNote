package parser;


import parser.ast.AstNode;
import parser.ast.AstNodeTypes;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class SimpleParser {
    //Expr -> digit + Expr / digit
    //digit -> 0/1/2/3/4/5/6/7/8/9
    public static AstNode parse(PeekTokenIterator it) throws ParseException {

        Expr expr = new Expr(null);
        Scalar scalar = new Scalar(expr, it); //吃掉第一个数字
        if (!it.hasNext()) {
            return scalar;
        }
        expr.setLexeme(it.peek());
        it.nextMatch("+");
        expr.setLabel("+");
        expr.addChild(scalar);
        expr.setType(AstNodeTypes.BINARY_EXPR);
        AstNode rightNode = parse(it);
        expr.addChild(rightNode);
        return expr;
    }

}
