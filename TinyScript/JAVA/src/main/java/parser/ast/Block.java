package parser.ast;


import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Block extends Stmt {

    public Block(AstNode parent) {
        super(parent, AstNodeTypes.BLOCK, "BLOCK");
    }

    public static AstNode parse(AstNode parent, PeekTokenIterator it) throws ParseException {
        it.nextMatch("{");
        Block block = new Block(parent);
        AstNode stmt = null;
        while ((stmt = Stmt.parseStmt(parent, it)) != null) {
            block.addChild(stmt);
        }
        it.nextMatch("}");
        return block;
    }
}
