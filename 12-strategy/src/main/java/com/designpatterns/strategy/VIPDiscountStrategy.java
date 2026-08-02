package com.designpatterns.strategy;

/**
 * VIP折扣策略 - 9折
 */
public class VIPDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.9;
    }

    @Override
    public String getDescription() {
        return "VIP用户 - 9折优惠";
    }
}
