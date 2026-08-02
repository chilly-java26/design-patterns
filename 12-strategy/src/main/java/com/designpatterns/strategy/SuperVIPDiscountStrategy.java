package com.designpatterns.strategy;

/**
 * 超级VIP折扣策略 - 8折
 */
public class SuperVIPDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.8;
    }

    @Override
    public String getDescription() {
        return "超级VIP用户 - 8折优惠";
    }
}
