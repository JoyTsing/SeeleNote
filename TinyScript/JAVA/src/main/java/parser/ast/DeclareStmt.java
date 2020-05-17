package parser.ast;

public class DeclareStmt extends Stmt{
    public DeclareStmt(AstNode parent) {
        super(parent,AstNodeTypes.DECLARE_STMT,"declare");
    }
}
