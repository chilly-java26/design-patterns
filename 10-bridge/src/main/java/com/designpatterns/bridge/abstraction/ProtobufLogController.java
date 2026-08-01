package com.designpatterns.bridge.abstraction;

import com.designpatterns.bridge.implementation.Storage;
import com.designpatterns.bridge.util.TimeUtil;

/**
 * Protobuf格式的日志控制器（具体实现）
 * 
 * 继承自LogController，实现Protobuf格式的日志处理。
 * 这是桥接模式中"抽象"维度的一个具体实现。
 * 
 * 注：这里使用模拟的Protobuf格式（实际应用中应使用真实的protobuf库）
 */
public class ProtobufLogController extends LogController {
    
    /**
     * 构造函数
     * @param storage 存储实现，通过父类构造函数注入
     */
    public ProtobufLogController(Storage storage) {
        super(storage);
    }

    /**
     * 实现日志处理逻辑
     * 1. 将原始日志格式化成Protobuf格式
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
        // 1. 将log格式化成Protobuf格式（模拟）
        String protobuf = formatToProtobuf(log);
        // 2. 调用storage.save()保存格式化后的数据
        storage.save(protobuf);
    }
    
    /**
     * 将日志格式化成Protobuf格式（模拟实现）
     * 实际应用中应使用protobuf库生成二进制数据
     * 
     * @param log 原始日志字符串
     * @return 模拟的Protobuf格式字符串
     */
    private String formatToProtobuf(String log) {
        // 模拟protobuf的文本表示
        return String.format("message: \"%s\" timestamp: %d", log, TimeUtil.current());
    }
}
