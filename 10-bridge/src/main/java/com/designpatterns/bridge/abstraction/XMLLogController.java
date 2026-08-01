package com.designpatterns.bridge.abstraction;

import com.designpatterns.bridge.implementation.Storage;
import com.designpatterns.bridge.util.TimeUtil;

/**
 * XML格式的日志控制器（具体实现）
 * 
 * 继承自LogController，实现XML格式的日志处理。
 * 这是桥接模式中"抽象"维度的另一个具体实现。
 */
public class XMLLogController extends LogController {
    
    /**
     * 构造函数
     * @param storage 存储实现，通过父类构造函数注入
     */
    public XMLLogController(Storage storage) {
        super(storage);
    }

    /**
     * 实现日志处理逻辑
     * 1. 将原始日志格式化成XML格式
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
        // 1. 将log格式化成XML格式
        String xml = formatToXML(log);
        // 2. 调用storage.save()保存格式化后的数据
        storage.save(xml);
    }
    
    /**
     * 将日志格式化成XML格式
     * @param log 原始日志字符串
     * @return XML格式的日志字符串，包含message和timestamp标签
     */
    private String formatToXML(String log) {
        return String.format("<log><message>%s</message><timestamp>%d</timestamp></log>", 
                escapeXml(log), TimeUtil.current());
    }
    
    /**
     * 转义XML特殊字符
     * @param text 原始文本
     * @return 转义后的文本
     */
    private String escapeXml(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}
