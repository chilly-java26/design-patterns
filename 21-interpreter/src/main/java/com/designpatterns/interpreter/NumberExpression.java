package com.designpatterns.interpreter;

/**
 * 终结符表达式 - 数字
 * 表示文法中的叶子节点，直接返回数字值
 */
public class NumberExpression implements Expression {
    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    @Override
    public int interpret() {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
