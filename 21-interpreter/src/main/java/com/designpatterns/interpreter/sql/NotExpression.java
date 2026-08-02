package com.designpatterns.interpreter.sql;

/**
 * NOT 表达式（非终结符表达式）
 * 表示逻辑非运算，对表达式结果取反
 */
public class NotExpression implements BooleanExpression {
    private BooleanExpression expression;

    public NotExpression(BooleanExpression expression) {
        this.expression = expression;
    }

    @Override
    public boolean interpret(Context context) {
        return !expression.interpret(context);
    }

    @Override
    public String toString() {
        return "NOT " + expression;
    }
}
