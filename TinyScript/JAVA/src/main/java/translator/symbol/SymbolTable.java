package translator.symbol;

import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;

public class SymbolTable {
    public SymbolTable parent = null;

    private ArrayList<SymbolTable> children;
    private ArrayList<Symbol> symbols;

    private int tempIndex = 0;
    private int offsetIndex = 0;//a b c d这种变量的映射抽象
    private int level = 0;

    public SymbolTable() {
        children = new ArrayList<>();
        symbols = new ArrayList<>();
    }

    public void addSymbol(Symbol symbol) {
        this.symbols.add(symbol);
        symbol.parent = this;
    }

    public boolean exists(Token lexeme) {
        var _symbol = this.symbols.stream().filter(
                x -> x.lexeme.getValue().equals(lexeme.getValue())
        ).findFirst();
        if (!_symbol.isEmpty()) {
            return true;
        }
        if (this.parent != null) {
            return this.parent.exists(lexeme);
        }
        return false;
    }

    /*
        var a=1;
        {
            {
                {
                    var b = a
                }
            }
        }
     */

    /**
     * @param lexeme
     * @param layoutOffset 向上追溯几个层级,也即时几层的父子嵌套关系,明确确定作用域
     * @return Symbol  也就是返回向上层级的那个引用
     */
    public Symbol cloneFromSymbolTree(Token lexeme, int layoutOffset) {
        var _symbol = this.symbols.stream().
                filter(x -> x.lexeme.getValue().equals(lexeme.getValue())).
                findFirst();

        if (!_symbol.isEmpty()) {//当前作用域
            var symbol = _symbol.get().copy();
            symbol.setLayerOffset(layoutOffset);
            return symbol;
        }

        if (this.parent != null) {
            return this.parent.cloneFromSymbolTree(lexeme, layoutOffset + 1);
        }

        return null;
    }

    //根据词法单元创建符号
    public Symbol createSymbolByLexeme(Token lexeme) {
        Symbol symbol = null;
        if (lexeme.isScalar()) {
            symbol = Symbol.createImmediateSymbol(lexeme);
        } else {
            symbol = cloneFromSymbolTree(lexeme, 0);
            if (symbol == null) {
                symbol = Symbol.createAddressSymbol(lexeme, offsetIndex++);
            }
        }
        symbols.add(symbol);
        return symbol;
    }

    /**
     *     创建临时变量
     */
    public Symbol createVariable(){
        var lexeme=new Token(TokenType.VARIABLE,"p"+tempIndex++);
        var symbol=Symbol.createAddressSymbol(lexeme,offsetIndex++);
        addSymbol(symbol);
        return symbol;
    }

    public void addChild(SymbolTable child){
        child.parent=this;
        child.level=level+1;
        children.add(child);
    }

    public int localSize(){
        return this.offsetIndex;
    }

    public ArrayList<SymbolTable> getChildren() {
        return children;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    public void createLabel(String label,Token lexeme){
        var labelSymbol=Symbol.createLabelSymbol(label, lexeme);
        addSymbol(labelSymbol);
    }
}
