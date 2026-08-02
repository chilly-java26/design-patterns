package com.designpatterns.interpreter;

/**
 * 非终结符表达式 - 加法运算
 * 持有两个子表达式，递归解释并返回相加结果
 */
public class AddExpression implements Expression {
    private Expression left;
    private Expression right;

    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() + right.interpret();
    }

    @Override
    public String toString() {
        return "(" + left + " + " + right + ")";
    }
}
