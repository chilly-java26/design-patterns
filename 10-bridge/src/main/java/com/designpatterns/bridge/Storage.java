package com.designpatterns.bridge;

public interface Storage {
    /**
     * 保存日志数据
     * @param log 格式化后的日志字符串
     */
    void save(String log);
}