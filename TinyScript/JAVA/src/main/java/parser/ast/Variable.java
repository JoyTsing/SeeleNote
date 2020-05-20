package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Variable extends Factor {
    private Token typeLexeme = null;

    public Variable(Token token) {
        super(token);
        this.type = AstNodeTypes.VARIABLE;
    }

    public void setTypeLexeme(Token token) {
        this.typeLexeme = token;
    }

    public Token getTypeLexeme() {
        return this.typeLexeme;
    }
}
