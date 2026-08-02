package com.designpatterns.interpreter.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文类
 * 存储变量的值，用于解释表达式
 */
public class Context {
    private Map<String, Object> variables = new HashMap<>();

    /**
     * 设置变量的值
     */
    public void set(String name, Object value) {
        variables.put(name, value);
    }

    /**
     * 获取变量的值
     */
    public Object get(String name) {
        return variables.get(name);
    }

    /**
     * 检查变量是否存在
     */
    public boolean contains(String name) {
        return variables.containsKey(name);
    }

    @Override
    public String toString() {
        return variables.toString();
    }
}
