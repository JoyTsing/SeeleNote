package parser.ast;


public class IfStmt extends Stmt{

    public IfStmt(AstNode parent) {
        super(parent, AstNodeTypes.IF_STMT, "if");
    }
}
