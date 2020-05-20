package parser.ast;


import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionArgs extends AstNode{
    public FunctionArgs(AstNode parent) {
        super(parent);
        this.label="args";
    }

    public static AstNode parse(AstNode parent, PeekTokenIterator it) throws ParseException {

        FunctionArgs args=new FunctionArgs(parent);
        while(it.peek().isType()){
            Token type = it.next();
            Variable variable = (Variable) Factor.parse(parent, it);
            variable.setTypeLexeme(type);
            args.addChild(variable);

            if(!it.peek().getValue().equals(")"))
                it.nextMatch(",");
        }
        return args;
    }
}
