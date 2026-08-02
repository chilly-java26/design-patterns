package com.designpatterns.strategy;

/**
 * 策略接口 - 定义所有支付策略的统一接口
 */
public interface PaymentStrategy {
    /**
     * 支付方法
     * @param amount 支付金额
     */
    void pay(double amount);
}
