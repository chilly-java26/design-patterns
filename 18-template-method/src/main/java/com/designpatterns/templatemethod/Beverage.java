package com.designpatterns.templatemethod;

/**
 * 饮料抽象类 - 定义制作饮料的模板方法
 * 模板方法定义了算法的骨架，具体步骤由子类实现
 */
public abstract class Beverage {
    
    /**
     * 模板方法 - 定义制作饮料的算法骨架
     * final 修饰，防止子类重写，保证流程不被改变
     */
    public final void makeBeverage() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
        
        // 钩子方法：可选步骤
        if (customerWantsCondiments()) {
            addExtraCondiments();
        }
    }
    
    /**
     * 具体方法 - 烧水（所有饮料都一样）
     */
    private void boilWater() {
        System.out.println("烧水到 100°C");
    }
    
    /**
     * 具体方法 - 倒入杯中（所有饮料都一样）
     */
    private void pourInCup() {
        System.out.println("倒入杯中");
    }
    
    /**
     * 抽象方法 - 冲泡（不同饮料冲泡方式不同）
     */
    protected abstract void brew();
    
    /**
     * 抽象方法 - 添加调料（不同饮料调料不同）
     */
    protected abstract void addCondiments();
    
    /**
     * 钩子方法 - 是否需要调料（子类可以覆盖此方法来改变行为）
     * 默认返回 true，子类可以重写来控制是否执行某些步骤
     */
    protected boolean customerWantsCondiments() {
        return true;
    }
    
    /**
     * 钩子方法 - 添加额外调料（可选步骤，默认什么都不做）
     */
    protected void addExtraCondiments() {
        // 默认不做任何事，子类可以选择性覆盖
    }
}
