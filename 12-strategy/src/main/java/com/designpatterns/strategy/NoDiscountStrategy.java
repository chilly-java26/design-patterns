package com.designpatterns.strategy;

/**
 * 无折扣策略 - 普通用户
 */
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice;
    }

    @Override
    public String getDescription() {
        return "普通用户 - 无折扣";
    }
}
