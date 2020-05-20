package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Program extends Block {

    public Program() {
        super();
    }

    public static AstNode parse(PeekTokenIterator it) throws ParseException {
        Program block = new Program();
        AstNode stmt = null;
        while ((stmt = Stmt.parseStmt(it)) != null) {
            block.addChild(stmt);
        }
        return block;
    }
}
