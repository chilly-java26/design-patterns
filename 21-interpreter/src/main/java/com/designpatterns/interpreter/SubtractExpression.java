package com.designpatterns.interpreter;

/**
 * 非终结符表达式 - 减法运算
 * 持有两个子表达式，递归解释并返回相减结果
 */
public class SubtractExpression implements Expression {
    private Expression left;
    private Expression right;

    public SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() - right.interpret();
    }

    @Override
    public String toString() {
        return "(" + left + " - " + right + ")";
    }
}
