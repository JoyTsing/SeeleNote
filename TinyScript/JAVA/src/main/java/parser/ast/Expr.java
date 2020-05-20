package parser.ast;

import lexer.Token;
import parser.util.ExprHOF;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;
import parser.util.PriorityTable;

public class Expr extends AstNode {

    private static PriorityTable table = new PriorityTable();

    public Expr(AstNode parent, AstNodeTypes type, Token lexeme) {
        super(parent);
        this.type = type;
        this.label = lexeme.getValue();
        this.lexeme = lexeme;
    }

    public Expr(AstNode parent) {
        super(parent);
    }

    //left:E(k)->E(k) op(k) E(k+1) |E(k+1)
    //right:
    //  E(k)->E(k+1) E_(k)
    // 都不为null时，翻译过程 new 一个表达式 其中他的left为Ek+1 他的操作为op right 为E(k+1) E_(k)
    //  E_(k) -> op(k) E(k+1) E_(k) |e
    //最高优先级处理（F为因子，U为一元表达式
    //E(t) -> F E_(k) | U E_(k)
    //E_(t) -> op(t) E(t) E_(t) |e
    private static AstNode E(AstNode parent, int k, PeekTokenIterator it) throws ParseException {
        if (k < table.size() - 1)
            return combine(parent, it, () -> E(parent, k + 1, it), () -> E_(parent, k, it));
        else
            return race(
                    it,
                    () -> combine(parent, it, () -> U(parent, it), () -> E_(parent, k, it)),
                    () -> combine(parent, it, () -> F(parent, it), () -> E_(parent, k, it))
            );

    }


    private static AstNode U(AstNode parent, PeekTokenIterator it) throws ParseException {
        Token token = it.peek();
        String value = token.getValue();
        AstNode expr = null;
        if (value.equals("(")) {
            it.nextMatch("(");
            expr = E(parent, 0, it);
            it.nextMatch(")");
            return expr;
        } else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            Token t = it.peek();
            it.nextMatch(value);
            Expr unaryExpr = new Expr(parent, AstNodeTypes.UNARY_EXPR, t);
            unaryExpr.addChild(E(parent, 0, it));
            return unaryExpr;
        }
        return null;
    }

    private static AstNode F(AstNode parent, PeekTokenIterator it) {
        Token token = it.peek();
        if (token.isVariable()) {
            return new Variable(parent, it);
        } else
            return new Scalar(parent, it);


    }

    private static AstNode combine(AstNode parent, PeekTokenIterator it, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        AstNode a = aFunc.hoc();
        if (a == null) {
            return it.hasNext() ? bFunc.hoc() : null;
        }
        AstNode b = it.hasNext() ? bFunc.hoc() : null;
        if (b == null) {
            return a;
        }
        Expr expr = new Expr(parent, AstNodeTypes.BINARY_EXPR, b.lexeme);
        expr.addChild(a);
        expr.addChild(b.getChildren(0));
        return expr;
    }

    private static AstNode E_(AstNode parent, int k, PeekTokenIterator it) throws ParseException {
        Token token = it.peek();
        String value = token.getValue();

        if (table.get(k).indexOf(value) != -1) {
            Expr expr = new Expr(parent, AstNodeTypes.BINARY_EXPR, it.nextMatch(value));
            expr.addChild(combine(parent, it,
                    () -> E(parent, k + 1, it),
                    () -> E_(parent, k, it)
            ));
            return expr;
        }
        return null;
    }

    private static AstNode race(PeekTokenIterator it, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        if (!it.hasNext()) {
            return null;
        }
        var a = aFunc.hoc();
        if (a != null)
            return a;
        return bFunc.hoc();
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        return E(null, 0, it);
    }

    public static AstNode parse(AstNode parent, PeekTokenIterator it) throws ParseException {
        return E(parent, 0, it);
    }
}
