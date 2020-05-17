package parser.ast;


public abstract class Stmt extends AstNode{

    public Stmt(AstNode parent, AstNodeTypes type, String label) {
        super(parent, type, label);
    }

}
