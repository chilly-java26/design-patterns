package com.designpatterns.iterator;

/**
 * 迭代器接口 - 定义遍历的标准操作
 */
public interface Iterator<T> {
    /**
     * 判断是否还有下一个元素
     */
    boolean hasNext();

    /**
     * 获取下一个元素
     */
    T next();
}
