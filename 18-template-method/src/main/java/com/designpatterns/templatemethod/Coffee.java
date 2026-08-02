package com.designpatterns.templatemethod;

/**
 * 咖啡类 - 实现咖啡的具体制作步骤
 */
public class Coffee extends Beverage {
    
    @Override
    protected void brew() {
        System.out.println("用热水冲泡咖啡粉");
    }
    
    @Override
    protected void addCondiments() {
        System.out.println("添加糖和牛奶");
    }
    
    @Override
    protected void addExtraCondiments() {
        System.out.println("撒上肉桂粉");
    }
}
