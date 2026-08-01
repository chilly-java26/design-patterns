package com.designpatterns.bridge.implementation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件存储实现
 * 
 * Storage接口的具体实现，将日志保存到文件。
 * 这是桥接模式中"实现"维度的一个具体实现。
 */
public class FileStorage implements Storage {
    /**
     * 日志文件路径
     */
    private String filePath;
    
    /**
     * 构造函数
     * @param filePath 日志文件的路径
     */
    public FileStorage(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * 保存日志到文件
     * 使用追加模式，每条日志前加上时间戳
     * 
     * @param log 格式化后的日志字符串
     * @throws IllegalArgumentException 如果log为null
     */
    @Override
    public void save(String log) {
        if (log == null) {
            throw new IllegalArgumentException("Log data cannot be null");
        }
        // 使用try-with-resources自动关闭文件流
        // 第二个参数true表示追加模式
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            // 添加时间戳
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println("[" + timestamp + "] " + log);
            System.out.println("日志已写入文件: " + filePath);
        } catch (IOException e) {
            System.err.println("写入文件失败: " + e.getMessage());
        }
    }
}
