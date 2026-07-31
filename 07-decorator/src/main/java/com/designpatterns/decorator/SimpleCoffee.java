package com.designpatterns.decorator;

/**
 * ConcreteComponent - 具体组件
 * 基础咖啡实现
 */
public class SimpleCoffee implements Coffee {
    
    @Override
    public String getDescription() {
        return "简单咖啡";
    }
    
    @Override
    public double getCost() {
        return 10.0;
    }
}
