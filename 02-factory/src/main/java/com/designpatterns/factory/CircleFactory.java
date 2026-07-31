package com.designpatterns.factory;

/**
 * 圆形工厂 - 枚举单例模式
 * 负责创建圆形对象
 */
public enum CircleFactory implements ShapeFactory {
    INSTANCE;
    
    @Override
    public Shape createShape() {
        return new Circle();
    }
}
