package com.designpatterns.decorator.javaio;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义输入流装饰器
 * 将读取的字符转换为大写
 * 
 * 继承 FilterInputStream 是创建 InputStream 装饰器的标准做法
 * FilterInputStream 本身就是一个装饰器基类
 */
public class UpperCaseInputStream extends FilterInputStream {
    
    /**
     * 构造函数
     * @param in 被装饰的输入流
     */
    public UpperCaseInputStream(InputStream in) {
        super(in);
    }
    
    /**
     * 重写 read() 方法，添加转大写功能
     */
    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1 ? c : Character.toUpperCase(c));
    }
    
    /**
     * 重写 read(byte[], int, int) 方法
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);
        for (int i = off; i < off + result; i++) {
            b[i] = (byte) Character.toUpperCase((char) b[i]);
        }
        return result;
    }
}
