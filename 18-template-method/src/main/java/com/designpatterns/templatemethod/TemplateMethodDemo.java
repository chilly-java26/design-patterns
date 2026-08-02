package com.designpatterns.templatemethod;

/**
 * 模板方法模式演示
 * 
 * 模板方法模式的核心思想：
 * 1. 在父类中定义算法的骨架（流程框架）
 * 2. 某些步骤延迟到子类实现
 * 3. 子类在不改变算法整体结构的前提下，可以重定义算法的某些特定步骤
 * 
 * 优点：
 * - 封装不变部分，扩展可变部分
 * - 提取公共代码，便于维护
 * - 行为由父类控制，子类实现
 * 
 * 缺点：
 * - 每个不同的实现都需要一个子类，导致类的个数增加
 * - 基于继承，耦合度较高
 */
public class TemplateMethodDemo {
    
    public static void main(String[] args) {
        System.out.println("===== 制作咖啡 =====");
        Beverage coffee = new Coffee();
        coffee.makeBeverage();
        
        System.out.println("\n===== 制作茶 =====");
        Beverage tea = new Tea();
        tea.makeBeverage();
        
        System.out.println("\n===== 制作热巧克力 =====");
        Beverage hotChocolate = new HotChocolate();
        hotChocolate.makeBeverage();
        
        // 演示模板方法的核心价值
        System.out.println("\n===== 模板方法的价值 =====");
        System.out.println("1. 所有饮料制作流程统一，不会出错");
        System.out.println("2. 子类只需关注自己的特殊步骤（冲泡方式、调料）");
        System.out.println("3. 客户端只需调用一个方法，自动按顺序执行所有步骤");
        System.out.println("4. 流程在父类中固定（final），子类无法改变顺序");
    }
}
