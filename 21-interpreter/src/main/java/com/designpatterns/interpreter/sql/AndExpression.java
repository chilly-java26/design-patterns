package com.designpatterns.interpreter.sql;

/**
 * AND 表达式（非终结符表达式）
 * 表示逻辑与运算，两个条件都满足才返回 true
 */
public class AndExpression implements BooleanExpression {
    private BooleanExpression left;
    private BooleanExpression right;

    public AndExpression(BooleanExpression left, BooleanExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Context context) {
        // 短路求值：如果左边为 false，直接返回 false
        return left.interpret(context) && right.interpret(context);
    }

    @Override
    public String toString() {
        return "(" + left + " AND " + right + ")";
    }
}
