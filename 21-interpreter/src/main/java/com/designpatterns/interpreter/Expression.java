package com.designpatterns.interpreter;

/**
 * 抽象表达式接口
 * 定义解释操作，所有表达式都要实现这个接口
 */
public interface Expression {
    /**
     * 解释表达式，计算结果
     * @return 计算结果
     */
    int interpret();
}
