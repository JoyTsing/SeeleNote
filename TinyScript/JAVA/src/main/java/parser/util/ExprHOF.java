package parser.util;

import parser.ast.AstNode;

//high order function
@FunctionalInterface
public interface ExprHOF {
    AstNode hoc()throws ParseException;
}
