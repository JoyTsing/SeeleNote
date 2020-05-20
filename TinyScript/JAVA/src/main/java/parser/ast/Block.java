package parser.ast;


import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Block extends Stmt {

    public Block() {
        super(AstNodeTypes.BLOCK, "block");
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        it.nextMatch("{");
        Block block = new Block();
        AstNode stmt = null;
        while ((stmt = Stmt.parseStmt(it)) != null) {
            block.addChild(stmt);
        }
        it.nextMatch("}");
        return block;
    }
}
