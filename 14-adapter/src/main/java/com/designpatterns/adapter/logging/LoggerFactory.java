package com.designpatterns.adapter.logging;

// 日志工厂
public class LoggerFactory {

    // 手动指定适配器类型
    public static Logger getLogger(Class<?> clazz, Class<? extends Logger> adapterClass) {
        String name = clazz.getName();
        try {
            // 通过反射创建指定的适配器实例
            return adapterClass.getConstructor(String.class).newInstance(name);
        } catch (Exception e) {
            throw new RuntimeException("无法创建适配器: " + adapterClass.getName(), e);
        }
    }

    // 使用默认适配器（可配置）
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz, JULAdapter.class); // 默认使用 JUL
    }
}
