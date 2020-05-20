package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

public class Scalar extends Factor {
    public Scalar(AstNode parent, PeekTokenIterator it) {
        super(parent,it);
    }

    public Scalar(AstNode parent, Token token) {
        super(parent,token);
        this.type = AstNodeTypes.SCALAR;
    }
}
