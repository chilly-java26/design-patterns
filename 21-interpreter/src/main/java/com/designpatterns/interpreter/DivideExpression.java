package com.designpatterns.interpreter;

/**
 * 非终结符表达式 - 除法运算
 * 持有两个子表达式，递归解释并返回相除结果
 */
public class DivideExpression implements Expression {
    private Expression left;
    private Expression right;

    public DivideExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        int divisor = right.interpret();
        if (divisor == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        return left.interpret() / divisor;
    }

    @Override
    public String toString() {
        return "(" + left + " / " + right + ")";
    }
}
