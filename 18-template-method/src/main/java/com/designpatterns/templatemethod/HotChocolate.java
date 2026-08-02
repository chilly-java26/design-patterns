package com.designpatterns.templatemethod;

/**
 * 热巧克力类 - 实现热巧克力的具体制作步骤
 */
public class HotChocolate extends Beverage {
    
    @Override
    protected void brew() {
        System.out.println("用热水冲泡巧克力粉");
    }
    
    @Override
    protected void addCondiments() {
        System.out.println("添加牛奶和糖");
    }
    
    @Override
    protected void addExtraCondiments() {
        System.out.println("加上棉花糖和奶油");
    }
}
