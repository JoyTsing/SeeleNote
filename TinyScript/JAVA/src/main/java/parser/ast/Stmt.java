package parser.ast;


import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public abstract class Stmt extends AstNode {

    public Stmt(AstNode parent, AstNodeTypes type, String label) {
        super(parent, type, label);
    }

    public static AstNode parseStmt(AstNode parent, PeekTokenIterator it) throws ParseException {
        var token = it.next();
        var lookahead = it.peek();
        it.putBack();
        if (token.isVariable() && lookahead.getValue().equals("=")) {
            return AssignStmt.parse(parent, it);
        } else if (token.getValue().equals("var")) {
            return DeclareStmt.parseStmt(parent, it);
        } else if (token.getValue().equals("func")) {
            return FunctionDeclareStmt.parse(parent, it);
        } else if (token.getValue().equals("return")) {
            return ReturnStmt.parse(parent, it);
        } else if (token.getValue().equals("if")) {
            return IfStmt.parse(parent, it);
        } else if (token.getValue().equals("{")) {
            return Block.parse(parent, it);

        }
            return null;
    }
}
