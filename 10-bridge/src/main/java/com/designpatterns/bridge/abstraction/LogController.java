package com.designpatterns.bridge.abstraction;

import com.designpatterns.bridge.implementation.Storage;

/**
 * 桥接模式 - 日志控制器（抽象部分）
 * 
 * 这是桥接模式中的"抽象"部分，代表日志收集的抽象概念。
 * 通过持有Storage接口的引用（桥梁），将抽象部分与实现部分分离。
 * 
 * 设计要点：
 * 1. 持有Storage接口引用（桥梁）- 存储维度通过组合实现
 * 2. 定义抽象的process方法 - 格式化维度通过继承实现
 * 3. 子类负责具体的格式化逻辑，父类负责与Storage的协调
 */
public abstract class LogController {
    /**
     * 桥梁：持有Storage接口的引用
     * 这是连接抽象部分和实现部分的关键
     * protected修饰符允许子类访问
     */
    protected Storage storage;

    /**
     * 构造函数，注入Storage依赖
     * @param storage 存储实现，可以是FileStorage、ConsoleStorage等任意实现
     * @throws IllegalArgumentException 如果storage为null
     */
    public LogController(Storage storage) {
        if (storage == null) {
            throw new IllegalArgumentException("Storage cannot be null");
        }
        this.storage = storage;
    }

    /**
     * 抽象方法：处理日志
     * 子类实现不同格式的日志处理逻辑（格式化 + 存储）
     * @param log 原始日志字符串
     * @throws IllegalArgumentException 如果log为null
     */
    public abstract void process(String log);
}
