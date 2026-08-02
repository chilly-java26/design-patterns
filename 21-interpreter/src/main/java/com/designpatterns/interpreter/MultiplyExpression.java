package com.designpatterns.interpreter;

/**
 * 非终结符表达式 - 乘法运算
 * 持有两个子表达式，递归解释并返回相乘结果
 */
public class MultiplyExpression implements Expression {
    private Expression left;
    private Expression right;

    public MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() * right.interpret();
    }

    @Override
    public String toString() {
        return "(" + left + " * " + right + ")";
    }
}
