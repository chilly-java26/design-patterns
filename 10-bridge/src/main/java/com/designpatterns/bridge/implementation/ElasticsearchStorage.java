package com.designpatterns.bridge.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Elasticsearch存储实现（模拟）
 * 
 * Storage接口的具体实现，模拟将日志发送到Elasticsearch。
 * 这是桥接模式中"实现"维度的一个具体实现。
 * 
 * 注：实际应用中应使用Elasticsearch的Java客户端
 */
public class ElasticsearchStorage implements Storage {
    
    private String host;
    private String index;
    
    /**
     * 构造函数
     * @param host Elasticsearch主机地址
     * @param index 索引名称
     */
    public ElasticsearchStorage(String host, String index) {
        this.host = host;
        this.index = index;
    }
    
    /**
     * 保存日志到Elasticsearch（模拟）
     * 实际应用中应通过HTTP客户端发送到ES
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
        System.out.println("[Elasticsearch] [" + timestamp + "] 发送到 " + host + "/" + index + ": " + log);
    }
}
