package parser.ast;

import lexer.Token;
import parser.util.ExprHOF;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;
import parser.util.PriorityTable;


public class Expr extends AstNode {

    private static PriorityTable table = new PriorityTable();


    public Expr() {
        super();
    }

    public Expr(AstNodeTypes type, Token lexeme) {
        super();
        this.type = type;
        this.label = lexeme.getValue();
        this.lexeme = lexeme;
    }

    // left:E(k) -> E(k) op(k) E(k+1) | E(k+1)
    // right:
    //    E(k) -> E(k+1) E_(k)
    //       var e = new Expr(); e.left = E(k+1); e.op = op(k); e.right = E(k+1) E_(k)
    //    E_(k) -> op(k) E(k+1) E_(k) | ε
    // 最高优先级处理:
    //    E(t) -> F E_(k) | U E_(k)
    //    E_(t) -> op(t) E(t) E_(t) | ε

    private static AstNode E(int k, PeekTokenIterator it) throws ParseException {
        if (k < table.size() - 1) {
            return combine(it, () -> E(k + 1, it), () -> E_(k, it));
        } else {
            return race(
                    it,
                    () -> combine(it, () -> F(it), () -> E_(k, it)),
                    () -> combine(it, () -> U(it), () -> E_(k, it))
            );
        }
    }

    private static AstNode E_(int k, PeekTokenIterator it) throws ParseException {
        var token = it.peek();
        var value = token.getValue();

        if (table.get(k).indexOf(value) != -1) {
            var expr = new Expr(AstNodeTypes.BINARY_EXPR, it.nextMatch(value));
            expr.addChild(combine(it,
                    () -> E(k + 1, it),
                    () -> E_(k, it)
            ));
            return expr;

        }
        return null;
    }

    private static AstNode U(PeekTokenIterator it) throws ParseException {
        var token = it.peek();
        var value = token.getValue();

        if (value.equals("(")) {
            it.nextMatch("(");
            var expr = E(0, it);
            it.nextMatch(")");
            return expr;
        } else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            var t = it.peek();
            it.nextMatch(value);
            Expr unaryExpr = new Expr(AstNodeTypes.UNARY_EXPR, t);
            unaryExpr.addChild(E(0, it));
            return unaryExpr;
        }
        return null;
    }

    //重构的重点
    private static AstNode F(PeekTokenIterator it) throws ParseException {
        var factor = Factor.parse(it);
        if (factor == null) {
            return null;
        }
        if (it.hasNext() && it.peek().getValue().equals("(")) {
            return CallExpr.parse(factor, it);
        }
        return factor;
    }

    private static AstNode combine(PeekTokenIterator it, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        var a = aFunc.hoc();
        if (a == null) {
            return it.hasNext() ? bFunc.hoc() : null;
        }
        var b = it.hasNext() ? bFunc.hoc() : null;
        if (b == null) {
            return a;
        }

        Expr expr = new Expr(AstNodeTypes.BINARY_EXPR, b.lexeme);
        expr.addChild(a);
        expr.addChild(b.getChildren(0));
        return expr;

    }

    private static AstNode race(PeekTokenIterator it, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        if (!it.hasNext()) {
            return null;
        }
        var a = aFunc.hoc();
        if (a != null) {
            return a;
        }
        return bFunc.hoc();
    }


    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        return E(0, it);

    }
}