package com.designpatterns.bridge.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 控制台存储实现
 * 
 * Storage接口的具体实现，将日志输出到控制台。
 * 这是桥接模式中"实现"维度的另一个具体实现。
 */
public class ConsoleStorage implements Storage {
    
    /**
     * 保存日志到控制台
     * 在控制台输出日志，带有[Console]标识和时间戳
     * 
     * @param log 格式化后的日志字符串
     * @throws IllegalArgumentException 如果log为null
     */
    @Override
    public void save(String log) {
        if (log == null) {
            throw new IllegalArgumentException("Log data cannot be null");
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[Console] [" + timestamp + "] " + log);
    }
}
