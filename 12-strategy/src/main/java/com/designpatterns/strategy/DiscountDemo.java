package com.designpatterns.strategy;

/**
 * 折扣策略演示
 * 展示策略模式如何消除if-else条件判断
 */
public class DiscountDemo {
    public static void main(String[] args) {
        double originalPrice = 1000.0;
        
        System.out.println("商品原价: " + originalPrice + " 元\n");
        System.out.println("========== 不使用策略模式 ==========");
        withoutStrategyPattern(originalPrice);
        
        System.out.println("\n========== 使用策略模式 ==========");
        withStrategyPattern(originalPrice);
    }

    /**
     * 不使用策略模式 - 使用if-else判断
     * 缺点：每增加一种用户类型，都要修改这个方法
     */
    private static void withoutStrategyPattern(double originalPrice) {
        String[] userTypes = {"NORMAL", "VIP", "SUPER_VIP"};
        
        for (String userType : userTypes) {
            double finalPrice;
            String description;
            
            // 大量的if-else判断
            if ("NORMAL".equals(userType)) {
                finalPrice = originalPrice;
                description = "普通用户 - 无折扣";
            } else if ("VIP".equals(userType)) {
                finalPrice = originalPrice * 0.9;
                description = "VIP用户 - 9折优惠";
            } else if ("SUPER_VIP".equals(userType)) {
                finalPrice = originalPrice * 0.8;
                description = "超级VIP用户 - 8折优惠";
            } else {
                finalPrice = originalPrice;
                description = "未知用户类型";
            }
            
            System.out.println(description + " → 实付: " + finalPrice + " 元");
        }
    }

    /**
     * 使用策略模式
     * 优点：新增用户类型只需新增策略类，不需要修改现有代码
     */
    private static void withStrategyPattern(double originalPrice) {
        DiscountStrategy[] strategies = {
            new NoDiscountStrategy(),
            new VIPDiscountStrategy(),
            new SuperVIPDiscountStrategy()
        };
        
        for (DiscountStrategy strategy : strategies) {
            double finalPrice = strategy.calculatePrice(originalPrice);
            System.out.println(strategy.getDescription() + " → 实付: " + finalPrice + " 元");
        }
    }
}
