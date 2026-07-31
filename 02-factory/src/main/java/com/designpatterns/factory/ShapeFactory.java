package com.designpatterns.factory;

/**
 * 工厂接口 - 定义创建产品的接口
 * 实现类负责创建具体的产品
 */
public interface ShapeFactory {
    
    /**
     * 工厂方法 - 由实现类来创建具体的形状对象
     * 
     * @return 具体的形状对象
     */
    Shape createShape();
}
