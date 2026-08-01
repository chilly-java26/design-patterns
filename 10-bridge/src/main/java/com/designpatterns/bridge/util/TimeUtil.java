package com.designpatterns.bridge.util;

/**
 * 时间工具类
 * 统一管理日志中的时间戳生成
 */
public class TimeUtil {
    
    /**
     * 获取当前时间戳（毫秒）
     * @return 当前时间的毫秒数
     */
    public static long current() {
        return System.currentTimeMillis();
    }
    
    /**
     * 私有构造函数，防止实例化
     */
    private TimeUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
