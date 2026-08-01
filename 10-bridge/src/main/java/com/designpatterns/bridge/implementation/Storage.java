package com.designpatterns.bridge.implementation;

/**
 * 存储接口（实现部分）
 * 
 * 这是桥接模式中的"实现"部分，定义了日志存储的抽象接口。
 * 不同的存储方式（文件、控制台、数据库等）实现此接口。
 * 
 * 桥接模式通过这个接口将抽象部分（LogController）与实现部分（具体存储）分离。
 */
public interface Storage {
    /**
     * 保存日志数据
     * @param log 格式化后的日志字符串
     */
    void save(String log);
}