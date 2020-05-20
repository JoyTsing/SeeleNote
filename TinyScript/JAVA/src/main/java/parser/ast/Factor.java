package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

public class Factor extends AstNode {

    public Factor(AstNode _parent, Token token) {
        super(_parent);
        this.lexeme = token;
        this.label = token.getValue();
    }

    public Factor(AstNode _parent, PeekTokenIterator it) {
        super(_parent);
        Token token = it.next();
        TokenType type = token.getType();

        if (type.equals(TokenType.VARIABLE)) {
            this.type = AstNodeTypes.VARIABLE;
        } else {
            this.type = AstNodeTypes.SCALAR;
        }
        this.label = token.getValue();
        this.lexeme = token;
    }


    public static AstNode parse(AstNode parent, PeekTokenIterator it) {
        var token = it.peek();
        var type = token.getType();

        if (type == TokenType.VARIABLE) {
            it.next();
            return new Variable(parent, token);
        } else if (token.isScalar()) {
            it.next();
            return new Scalar(parent, token);
        }
        return null;
    }
}
