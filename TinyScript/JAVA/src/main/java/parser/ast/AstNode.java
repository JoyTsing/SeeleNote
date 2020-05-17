package parser.ast;


import lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public abstract class AstNode {
    protected ArrayList<AstNode> children= new ArrayList<>();
    protected AstNode parent;

    /*关键信息*/
    protected Token lexeme;//词法单元
    protected String label;//备注
    protected AstNodeTypes type;

    public AstNode(AstNode parent,AstNodeTypes type,String label) {
        this.parent = parent;
        this.label = label;
        this.type = type;
    }

    public AstNode(AstNode parent) {
        this.parent = parent;
    }

    public AstNode getChildren(int index) {
        return this.children.get(index);
    }

    public void addChild(AstNode node){
        node.parent = this;
        children.add(node);
    }

    public void setLexeme(Token lexeme) {
        this.lexeme = lexeme;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(AstNodeTypes type) {
        this.type = type;
    }

    public Token getLexeme() {
        return lexeme;
    }

    public ArrayList<AstNode> getChildren() {
        return children;
    }

    public void print(int indent){
        System.out.println(StringUtils.leftPad("",indent*2)+label);
        for(var child:children){
            child.print(indent+1);
        }
    }
}
