package com.designpatterns.bridge.implementation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * S3存储实现（模拟）
 * 
 * Storage接口的具体实现，模拟将日志上传到AWS S3。
 * 这是桥接模式中"实现"维度的一个具体实现。
 * 
 * 注：实际应用中应使用AWS SDK for Java
 */
public class S3Storage implements Storage {
    
    private String bucket;
    private String region;
    private String keyPrefix;
    
    /**
     * 构造函数
     * @param bucket S3 bucket名称
     * @param region AWS区域
     * @param keyPrefix S3对象key的前缀（例如 "logs/"）
     */
    public S3Storage(String bucket, String region, String keyPrefix) {
        this.bucket = bucket;
        this.region = region;
        this.keyPrefix = keyPrefix;
    }
    
    /**
     * 保存日志到S3（模拟）
     * 实际应用中应通过AWS SDK上传对象到S3
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
        String key = keyPrefix + System.currentTimeMillis() + ".log";
        System.out.println("[S3] [" + timestamp + "] 上传到 s3://" + bucket + "/" + key + " (region: " + region + "): " + log);
    }
}
