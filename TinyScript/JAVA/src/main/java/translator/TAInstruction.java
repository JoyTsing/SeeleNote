package translator;


import org.apache.commons.lang3.NotImplementedException;
import translator.symbol.Symbol;

public class TAInstruction {//构造一个对象 一个对象代表一个指令

    private Object arg1;
    private Object arg2;
    private String op;
    private Symbol result;
    private TAInstructionType type;

    public TAInstruction(TAInstructionType type, Symbol result, String op, Object arg1, Object arg2) {
        this.op = op;
        this.type = type;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    public Symbol getResult() {
        return result;
    }

    public void setArg1(Object arg) {
        this.arg1 = arg;
    }

    public Object getArg1() {
        return this.arg1;
    }

    public void setArg2(Object arg) {
        this.arg2 = arg;
    }

    public Object getArg2() {
        return this.arg2;
    }

    public void setResult(Symbol address) {
        this.result = address;

    }

    public TAInstructionType getType() {
        return this.type;
    }

    public String getOp() {
        return this.op;
    }

    @Override
    public String toString() {
        switch (this.type) {
            case ASSIGN:
                if (arg2 != null) {
                    return String.format("%s = %s %s %s", result, arg1, op, arg2);
                } else {
                    return String.format("%s = %s", result, arg1);
                }
            case IF:
                return String.format("IF %s ELSE %s", arg1, arg2);
            case GOTO:
                return String.format("GOTO %s", arg1);
            case LABEL:
                return arg1 + ":";
            case FUNC_BEGIN:
                return "FUNC_BEGIN";
            case RETURN:
                return "RETURN " + arg1;
            case PARAM:
                return "PARAM " + arg1 + " " + arg2;//arg2代表的是传参的顺序 传进去的第几个参数
            case CALL:
                return "CALL " + arg1;
            case SP:
                return "SP " + arg1;
        }
        throw new NotImplementedException("Unknown instruction type:" + type);
    }
}
