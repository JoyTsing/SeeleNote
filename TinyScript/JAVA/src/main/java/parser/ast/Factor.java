package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

public abstract class Factor extends AstNode {

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
        this.lexeme=token;
    }

}
