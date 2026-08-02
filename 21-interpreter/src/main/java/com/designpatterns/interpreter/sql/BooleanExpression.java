package com.designpatterns.interpreter.sql;

/**
 * 抽象布尔表达式接口
 * 所有 SQL 条件表达式都实现这个接口
 */
public interface BooleanExpression {
    /**
     * 解释表达式，判断是否满足条件
     * 
     * @param context 上下文，包含变量的值
     * @return true 表示满足条件，false 表示不满足
     */
    boolean interpret(Context context);

    /**
     * 返回表达式的字符串表示
     */
    String toString();
}
