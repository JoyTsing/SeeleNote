package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionArgs extends AstNode{
    public FunctionArgs() {
        super();
        this.label="args";
    }

    public static AstNode parse( PeekTokenIterator it) throws ParseException {

        FunctionArgs args=new FunctionArgs();
        while(it.peek().isType()){
            var type = it.next();
            Variable variable = (Variable) Factor.parse(it);
            variable.setTypeLexeme(type);
            args.addChild(variable);

            if(!it.peek().getValue().equals(")"))
                it.nextMatch(",");
        }
        return args;
    }
}
