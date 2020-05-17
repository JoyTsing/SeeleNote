package lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Keywords {
    private static String[] keywords={
            "var",
            "if",
            "else",
            "for",
            "while",
            "break",
            "func",
            "return"
    };
    static HashSet<String> set=new HashSet<>(Arrays.asList(keywords));

    public static boolean isKeyword(String word){
        return set.contains(word);
    }
}