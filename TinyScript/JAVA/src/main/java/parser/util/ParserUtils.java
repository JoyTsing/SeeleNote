package parser.util;

import org.apache.commons.lang3.StringUtils;
import parser.ast.AstNode;
import parser.ast.Factor;

import java.util.ArrayList;

public class ParserUtils {
    public static String toPostfixExpression(AstNode node) {
        if (node instanceof Factor) {
            return node.getLexeme().getValue();
        }

        var prts = new ArrayList<String>();
        for (var child : node.getChildren()) {
            prts.add(toPostfixExpression(child));
        }
        var lexemeStr = node.getLexeme() != null ? node.getLexeme().getValue() : "";
        if (lexemeStr.length() > 0) {
            return StringUtils.join(prts, " ") + " " + lexemeStr;
        } else {
            return StringUtils.join(prts, " ");
        }
    }
}
