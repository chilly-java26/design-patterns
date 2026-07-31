package com.designpatterns.decorator;

/**
 * 装饰器模式演示
 */
public class DecoratorDemo {
    
    public static void main(String[] args) {
        // 1. 基础咖啡
        Coffee coffee = new SimpleCoffee();
        System.out.println("订单1: " + coffee.getDescription());
        System.out.println("价格: ¥" + coffee.getCost());
        System.out.println();
        
        // 2. 加牛奶的咖啡
        Coffee milkCoffee = new MilkDecorator(new SimpleCoffee());
        System.out.println("订单2: " + milkCoffee.getDescription());
        System.out.println("价格: ¥" + milkCoffee.getCost());
        System.out.println();
        
        // 3. 加糖的咖啡
        Coffee sugarCoffee = new SugarDecorator(new SimpleCoffee());
        System.out.println("订单3: " + sugarCoffee.getDescription());
        System.out.println("价格: ¥" + sugarCoffee.getCost());
        System.out.println();
        
        // 4. 加牛奶和糖的咖啡（多重装饰）
        Coffee milkSugarCoffee = new MilkDecorator(new SugarDecorator(new SimpleCoffee()));
        System.out.println("订单4: " + milkSugarCoffee.getDescription());
        System.out.println("价格: ¥" + milkSugarCoffee.getCost());
        System.out.println();
        
        // 5. 双倍牛奶加糖的咖啡
        Coffee doubleMilkSugarCoffee = new MilkDecorator(
            new MilkDecorator(
                new SugarDecorator(new SimpleCoffee())
            )
        );
        System.out.println("订单5: " + doubleMilkSugarCoffee.getDescription());
        System.out.println("价格: ¥" + doubleMilkSugarCoffee.getCost());
    }
}
