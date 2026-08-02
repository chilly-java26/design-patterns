package com.designpatterns.interpreter.sql;

/**
 * OR 表达式（非终结符表达式）
 * 表示逻辑或运算，只要有一个条件满足就返回 true
 */
public class OrExpression implements BooleanExpression {
    private BooleanExpression left;
    private BooleanExpression right;

    public OrExpression(BooleanExpression left, BooleanExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Context context) {
        // 短路求值：如果左边为 true，直接返回 true
        return left.interpret(context) || right.interpret(context);
    }

    @Override
    public String toString() {
        return "(" + left + " OR " + right + ")";
    }
}
