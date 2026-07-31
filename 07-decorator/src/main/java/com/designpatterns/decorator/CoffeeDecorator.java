package com.designpatterns.decorator;

/**
 * Decorator - 装饰器抽象类
 * 持有一个Component对象的引用，并定义一个与Component接口一致的接口
 */
public abstract class CoffeeDecorator implements Coffee {
    
    protected Coffee decoratedCoffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}
