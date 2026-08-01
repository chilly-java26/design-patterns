package com.designpatterns.bridge.abstraction;

import com.designpatterns.bridge.implementation.Storage;
import com.designpatterns.bridge.util.TimeUtil;

/**
 * 纯文本格式的日志控制器（具体实现）
 * 
 * 继承自LogController，实现纯文本格式的日志处理。
 * 这是桥接模式中"抽象"维度的一个具体实现。
 */
public class PlainTextLogController extends LogController {
    
    /**
     * 构造函数
     * @param storage 存储实现，通过父类构造函数注入
     */
    public PlainTextLogController(Storage storage) {
        super(storage);
    }

    /**
     * 实现日志处理逻辑
     * 1. 将原始日志格式化成纯文本格式
     * 2. 通过storage保存格式化后的日志
     * 
     * @param log 原始日志字符串
     * @throws IllegalArgumentException 如果log为null
     */
    @Override
    public void process(String log) {
        if (log == null) {
            throw new IllegalArgumentException("Log cannot be null");
        }
        // 1. 将log格式化成纯文本格式
        String plainText = formatToPlainText(log);
        // 2. 调用storage.save()保存格式化后的数据
        storage.save(plainText);
    }
    
    /**
     * 将日志格式化成纯文本格式
     * @param log 原始日志字符串
     * @return 纯文本格式的日志字符串，包含时间戳和消息
     */
    private String formatToPlainText(String log) {
        return String.format("[%d] %s", TimeUtil.current(), log);
    }
}
