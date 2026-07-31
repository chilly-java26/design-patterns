package com.designpatterns.factory;

/**
 * 矩形工厂 - 枚举单例模式
 * 负责创建矩形对象
 */
public enum RectangleFactory implements ShapeFactory {
    INSTANCE;
    
    @Override
    public Shape createShape() {
        return new Rectangle();
    }
}
