package parser.ast;


public class FunctionDeclareStmt extends Stmt{
    public FunctionDeclareStmt(AstNode parent) {
        super(parent, AstNodeTypes.FUNCTION_DECLARE_STMT, "func");
    }
}
