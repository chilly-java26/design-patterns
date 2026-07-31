package com.designpatterns.factory;

/**
 * 正方形工厂 - 枚举单例模式
 * 负责创建正方形对象
 */
public enum SquareFactory implements ShapeFactory {
    INSTANCE;
    
    @Override
    public Shape createShape() {
        return new Square();
    }
}
