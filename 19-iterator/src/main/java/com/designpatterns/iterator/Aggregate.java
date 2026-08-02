package com.designpatterns.iterator;

/**
 * 聚合对象接口 - 定义创建迭代器的方法
 */
public interface Aggregate<T> {
    /**
     * 创建迭代器
     */
    Iterator<T> iterator();
}
