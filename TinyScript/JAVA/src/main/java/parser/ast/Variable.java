package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Variable extends Factor {
    private Token typeLexeme = null;

    public Variable(AstNode parent, PeekTokenIterator it) {
        super(parent, it);
    }

    public Variable(AstNode parent, Token token) {
        super(parent, token);
        this.type = AstNodeTypes.VARIABLE;
    }

    public void setTypeLexeme(Token token) {
        this.typeLexeme = token;
    }

    public Token getTypeLexeme() {
        return this.typeLexeme;
    }
}
