package parser.util;

import org.apache.commons.lang3.StringUtils;
import parser.ast.AstNode;
import parser.ast.Factor;
import parser.ast.FunctionDeclareStmt;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public static String toBFSString(AstNode root, int max) {

        var queue = new LinkedList<AstNode>();
        var list = new ArrayList<String>();
        queue.add(root);

        int c = 0;
        while(queue.size() > 0 && c++ < max) {
            var node = queue.poll();
            list.add(node.getLabel());
            for(var child : node.getChildren()) {
                queue.add(child);
            }
        }
        return StringUtils.join(list, " ");
    }
}
