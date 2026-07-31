package com.designpatterns.decorator;

/**
 * ConcreteDecorator - 具体装饰器
 * 糖装饰器，为咖啡添加糖
 */
public class SugarDecorator extends CoffeeDecorator {
    
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", 加糖";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 1.0;
    }
}
