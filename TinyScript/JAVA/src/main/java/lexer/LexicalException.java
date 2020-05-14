package lexer;

public class LexicalException extends Exception{
    private String msg;

    public LexicalException(String _msg){
        msg=_msg;
    }

    public LexicalException(char c){
        msg=String.format("Unexpected character %c",c);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
