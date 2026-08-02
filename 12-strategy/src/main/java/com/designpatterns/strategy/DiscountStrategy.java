package com.designpatterns.strategy;

/**
 * 折扣策略接口
 * 进阶示例：展示策略模式在价格计算中的应用
 */
public interface DiscountStrategy {
    /**
     * 计算折后价
     * @param originalPrice 原价
     * @return 折后价
     */
    double calculatePrice(double originalPrice);
    
    /**
     * 获取策略描述
     * @return 描述文字
     */
    String getDescription();
}
