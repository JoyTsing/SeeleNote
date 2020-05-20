package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Program extends Block {

    public Program(AstNode parent) {
        super(parent);
    }

    public static AstNode parse(AstNode parent, PeekTokenIterator it) throws ParseException {
        Program block = new Program(parent);
        AstNode stmt = null;
        while ((stmt = Stmt.parseStmt(parent, it)) != null) {
            block.addChild(stmt);
        }
        return block;
    }
}
