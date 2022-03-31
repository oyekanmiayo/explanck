package com.ayomideoyekanmi.explanck.interpreter;

/**
 * @author: aoyekanmi
 * @date: 11/03/2022
 */
public class Interpreter implements Expr.Visitor<Object> {

    String interpret(Expr expr) {
        Object value = evaluate(expr);
        return value.toString();
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case MINUS:
                return (double) left - (double) right;
            case PLUS:
                return (double) left + (double) right;
            case SLASH:
                return (double) left / (double) right;
            case STAR:
                return (double) left * (double) right;
            case POWER:
                return Math.pow((double) left, (double) right);
        }

        return null;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }
}
