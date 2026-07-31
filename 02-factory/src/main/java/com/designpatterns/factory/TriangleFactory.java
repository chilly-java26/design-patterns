package com.designpatterns.factory;

/**
 * 三角形工厂 - 枚举单例模式
 * 负责创建三角形对象
 * 新增产品时，只需添加产品类和对应的工厂类，无需修改现有代码
 */
public enum TriangleFactory implements ShapeFactory {
    INSTANCE;
    
    @Override
    public Shape createShape() {
        return new Triangle();
    }
}
