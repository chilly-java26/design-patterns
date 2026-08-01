package com.designpatterns.bridge;

/**
 * 桥接模式 - 日志控制器（抽象部分）
 * 
 * TODO: 在这里实现桥接模式
 */
public abstract class LogController {
    // 桥梁：持有Storage接口的引用
    protected Storage storage;

    // 构造函数，注入storage依赖
    public LogController(Storage storage) {
        this.storage = storage;
    }

    // 抽象方法：子类实现不同格式的日志处理逻辑
    public abstract void process(String log);
}
