package com.designpatterns.factory;

/**
 * 简单工厂 - 枚举单例模式
 * 根据 Shape 类型创建对应的实例
 * 类似 LoggerFactory.getLogger(Class) 的使用方式
 */
public enum SimpleShapeFactory {
    INSTANCE;
    
    /**
     * 静态方法：获取 Shape 实例
     * 类似 LoggerFactory.getLogger(Class) 的调用风格
     * 
     * @param shapeClass Shape 的 Class 对象
     * @param <T> Shape 的子类型
     * @return 对应的 Shape 实例
     */
    public static <T extends Shape> T getShape(Class<T> shapeClass) {
        try {
            // 通过反射创建实例
            return shapeClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to create shape: " + shapeClass.getName(), e);
        }
    }
}
