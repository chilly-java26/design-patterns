package com.designpatterns.templatemethod;

/**
 * 茶类 - 实现茶的具体制作步骤
 */
public class Tea extends Beverage {
    
    @Override
    protected void brew() {
        System.out.println("用热水浸泡茶叶");
    }
    
    @Override
    protected void addCondiments() {
        System.out.println("添加柠檬片");
    }
    
    /**
     * 重写钩子方法 - 茶通常不需要额外调料
     */
    @Override
    protected boolean customerWantsCondiments() {
        return false;
    }
}
