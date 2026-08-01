package com.designpatterns.bridge.abstraction;

import com.designpatterns.bridge.implementation.Storage;
import com.designpatterns.bridge.util.TimeUtil;

/**
 * JSON格式的日志控制器（具体实现）
 * 
 * 继承自LogController，实现JSON格式的日志处理。
 * 这是桥接模式中"抽象"维度的一个具体实现。
 */
public class JSONLogController extends LogController {
    
    /**
     * 构造函数
     * @param storage 存储实现，通过父类构造函数注入
     */
    public JSONLogController(Storage storage) {
        super(storage);
    }

    /**
     * 实现日志处理逻辑
     * 1. 将原始日志格式化成JSON格式
     * 2. 通过storage保存格式化后的日志
     * 
     * @param log 原始日志字符串
     * @throws IllegalArgumentException 如果log为null
     */
    @Override
    public void process(String log){
        if (log == null) {
            throw new IllegalArgumentException("Log cannot be null");
        }
        // 1. 将log格式化成JSON格式
        String json = formatToJSON(log);
        // 2. 调用storage.save()保存格式化后的数据
        storage.save(json);
    }
    
    /**
     * 将日志格式化成JSON格式
     * @param log 原始日志字符串
     * @return JSON格式的日志字符串，包含message和timestamp字段
     */
    private String formatToJSON(String log) {
        return String.format("{\"message\":\"%s\",\"timestamp\":%d}", log, TimeUtil.current());
    }
}