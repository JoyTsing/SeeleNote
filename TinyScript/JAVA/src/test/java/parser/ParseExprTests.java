package parser;


import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.ast.AstNode;
import parser.ast.Expr;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

public class ParseExprTests {

    private AstNode createExpr(String src) throws LexicalException, ParseException {
        var lexer = new Lexer();
        var tokens = lexer.analyse(src.chars().mapToObj(x -> (char) x));
        var tokenIt = new PeekTokenIterator(tokens.stream());
        return Expr.parse(tokenIt);
    }

    @Test
    public void simple() throws LexicalException, ParseException {
        var expr = createExpr("1+1+1");
        Assertions.assertEquals("1 1 1 + +", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    public void simple1() throws LexicalException, ParseException {
        var expr = createExpr("\"1\" == \"\"");
        Assertions.assertEquals("\"1\" \"\" ==", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    public void complex() throws LexicalException, ParseException {
        var expr1 = createExpr("1+2*3");
        var expr2 = createExpr("1*2+3");
        var expr3 = createExpr("10 * (7 + 4 ) ");
        var expr4 = createExpr("(1*2!=7)==3!=4*5+6");

        Assertions.assertEquals("1 2 3 * +", ParserUtils.toPostfixExpression(expr1));
        Assertions.assertEquals("1 2 * 3 +", ParserUtils.toPostfixExpression(expr2));
        Assertions.assertEquals("10 7 4 + *", ParserUtils.toPostfixExpression(expr3));
        Assertions.assertEquals("1 2 * 7 != 3 4 5 * 6 + != ==", ParserUtils.toPostfixExpression(expr4));

    }
}
