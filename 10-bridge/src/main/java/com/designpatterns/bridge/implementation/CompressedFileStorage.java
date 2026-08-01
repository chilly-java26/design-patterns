package com.designpatterns.bridge.implementation;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩文件存储实现
 * 
 * 直接将日志写入gzip压缩文件（.gz格式）。
 * 使用持久化的压缩流，避免多次创建GZIP头部的开销。
 * 
 * <h3>特点：</h3>
 * <ul>
 *   <li>使用gzip压缩算法</li>
 *   <li>文件名自动添加.gz后缀</li>
 *   <li>持久化压缩流（高效）</li>
 *   <li>自动添加时间戳</li>
 *   <li>使用完毕后需要调用close()关闭流</li>
 * </ul>
 * 
 * <h3>压缩效果：</h3>
 * <ul>
 *   <li>文本日志通常可压缩50%-70%</li>
 *   <li>JSON/XML格式压缩效果更好（重复标签多）</li>
 *   <li>避免了多个GZIP头的开销</li>
 * </ul>
 * 
 * <h3>使用示例：</h3>
 * <pre>
 * // 创建压缩文件存储（会写入 logs/app.log.gz）
 * CompressedFileStorage storage = new CompressedFileStorage("logs/app.log");
 * 
 * // 写入多条日志
 * LogController controller = new JSONLogController(storage);
 * controller.process("log 1");
 * controller.process("log 2");
 * 
 * // 使用完毕后关闭（重要！）
 * storage.close();
 * </pre>
 * 
 * <h3>重要提示：</h3>
 * <ul>
 *   <li>使用完毕后必须调用close()关闭流</li>
 *   <li>或者使用try-with-resources自动关闭</li>
 *   <li>未关闭会导致数据丢失或文件损坏</li>
 * </ul>
 * 
 * @see FileStorage
 * @see java.util.zip.GZIPOutputStream
 */
public class CompressedFileStorage implements Storage, AutoCloseable {
    /**
     * 日志文件路径（不含.gz后缀）
     */
    private String filePath;
    
    /**
     * 持久化的压缩流
     */
    private PrintWriter writer;
    
    /**
     * 标记流是否已关闭
     */
    private boolean closed = false;
    
    /**
     * 构造函数
     * 
     * @param filePath 日志文件的路径（会自动添加.gz后缀）
     *                 例如："logs/app.log" → 实际文件："logs/app.log.gz"
     * @throws IllegalArgumentException 如果filePath为null或空
     */
    public CompressedFileStorage(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        this.filePath = filePath;
        initializeStream();
    }
    
    /**
     * 初始化压缩流
     * 创建持久化的GZIP输出流，避免每次save()都创建新流
     */
    private void initializeStream() {
        try {
            String gzFilePath = filePath + ".gz";
            FileOutputStream fos = new FileOutputStream(gzFilePath, false); // 覆盖模式
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            GZIPOutputStream gzos = new GZIPOutputStream(bos);
            OutputStreamWriter osw = new OutputStreamWriter(gzos, StandardCharsets.UTF_8);
            this.writer = new PrintWriter(osw);
            
            System.out.println("压缩文件流已创建: " + gzFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create compressed file: " + e.getMessage(), e);
        }
    }
    
    /**
     * 保存日志到压缩文件
     * 
     * 实现流程：
     * 1. 参数校验
     * 2. 添加时间戳
     * 3. 写入持久化的压缩流
     * 4. 刷新缓冲区
     * 
     * 注意：使用持久化流，所有日志写入同一个GZIP块，压缩效率高。
     * 
     * @param log 格式化后的日志字符串
     * @throws IllegalArgumentException 如果log为null
     * @throws IllegalStateException 如果流已关闭
     */
    @Override
    public void save(String log) {
        if (log == null) {
            throw new IllegalArgumentException("Log data cannot be null");
        }
        
        if (closed) {
            throw new IllegalStateException("CompressedFileStorage is closed");
        }
        
        // 添加时间戳并写入
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.println("[" + timestamp + "] " + log);
        
        // 刷新缓冲区，确保数据写入（但不关闭流）
        writer.flush();
    }
    
    /**
     * 关闭压缩流
     * 
     * 重要：使用完毕后必须调用此方法，否则：
     * - 数据可能丢失（缓冲区未刷新）
     * - 文件可能损坏（GZIP尾部未写入）
     * 
     * 此方法是幂等的，可以多次调用。
     */
    @Override
    public void close() {
        if (!closed && writer != null) {
            writer.close();
            closed = true;
            System.out.println("压缩文件流已关闭: " + filePath + ".gz");
        }
    }
}
