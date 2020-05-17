package parser.ast;

import parser.util.PeekTokenIterator;

public class Scalar extends Factor {
    public Scalar(AstNode parent, PeekTokenIterator it) {
        super(parent,it);
    }
}
