package parser.ast;


import lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AstNode {
    protected ArrayList<AstNode> children= new ArrayList<>();
    protected AstNode parent;

    /*关键信息*/
    protected Token lexeme;//词法单元
    protected String label;//备注
    protected AstNodeTypes type;// 类型

    private HashMap<String, Object> _props = new HashMap<>();

    public AstNode() {
    }

    public AstNode(AstNodeTypes type,String label) {
        this.label = label;
        this.type = type;
    }

    public AstNode getChildren(int index) {
        if(index >= this.children.size()) {
            return null;
        }
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
        if(indent == 0) {
            System.out.println("print:" + this);
        }

        System.out.println(StringUtils.leftPad(" ", indent *2) + label);
        for(var child : children) {
            child.print(indent + 1);
        }
    }

    public void replaceChild(int i, AstNode node) {
        this.children.set(i, node);
    }

    public HashMap<String, Object> props() {
        return this._props;
    }

    public Object getProp(String key) {
        if(!this._props.containsKey(key)) {
            return null;
        }
        return this._props.get(key);
    }

    public void setProp(String key, Object value) {
        this._props.put(key, value);
    }

    public boolean isValueType() {
        return this.type == AstNodeTypes.VARIABLE || this.type == AstNodeTypes.SCALAR;
    }

    public String getLabel() {
        return label;
    }

    public AstNodeTypes getType() {
        return type;
    }
}
