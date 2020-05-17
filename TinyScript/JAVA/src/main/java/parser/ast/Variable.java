package parser.ast;

import parser.util.PeekTokenIterator;

public class Variable extends Factor{
    public Variable(AstNode parent, PeekTokenIterator it) {
        super(parent,it);
    }
}
