package com.designpatterns.bridge.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Kafka存储实现（模拟）
 * 
 * Storage接口的具体实现，模拟将日志发送到Kafka。
 * 这是桥接模式中"实现"维度的一个具体实现。
 * 
 * 注：实际应用中应使用Kafka的Producer客户端
 */
public class KafkaStorage implements Storage {
    
    private String broker;
    private String topic;
    
    /**
     * 构造函数
     * @param broker Kafka broker地址
     * @param topic Topic名称
     */
    public KafkaStorage(String broker, String topic) {
        this.broker = broker;
        this.topic = topic;
    }
    
    /**
     * 保存日志到Kafka（模拟）
     * 实际应用中应通过KafkaProducer发送消息
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
        System.out.println("[Kafka] [" + timestamp + "] 发送到 " + broker + "/" + topic + ": " + log);
    }
}
