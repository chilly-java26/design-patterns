package com.designpatterns.flyweight.util;

/**
 * 字符串工具类 - 兼容 Java 8
 */
public class StringUtil {
    
    /**
     * 重复字符串 n 次（Java 11 String.repeat() 的兼容实现）
     */
    public static String repeat(String str, int count) {
        if (count <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
