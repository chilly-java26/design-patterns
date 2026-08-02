package com.designpatterns.strategy;

/**
 * 上下文类 - 购物车
 * 使用策略对象，可以在运行时切换不同的支付策略
 */
public class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    /**
     * 设置支付策略
     * @param paymentStrategy 支付策略
     */
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * 执行支付
     * @param amount 金额
     */
    public void checkout(double amount) {
        if (paymentStrategy == null) {
            System.out.println("请先选择支付方式！");
            return;
        }
        System.out.println("\n========== 开始支付 ==========");
        paymentStrategy.pay(amount);
        System.out.println("========== 支付完成 ==========\n");
    }
}
