package parser.ast;

public class ForStmt extends Stmt{
    public ForStmt(AstNode parent) {
        super(parent, AstNodeTypes.FOR_STMT, "for");
    }
}
