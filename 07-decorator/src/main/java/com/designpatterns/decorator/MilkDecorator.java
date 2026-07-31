package com.designpatterns.decorator;

/**
 * ConcreteDecorator - 具体装饰器
 * 牛奶装饰器，为咖啡添加牛奶
 */
public class MilkDecorator extends CoffeeDecorator {
    
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", 加牛奶";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 2.0;
    }
}
