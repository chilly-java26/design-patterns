package com.designpatterns.strategy;

/**
 * 具体策略 - 支付宝支付
 */
public class AlipayPayment implements PaymentStrategy {
    private String email;

    public AlipayPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("使用支付宝支付 " + amount + " 元");
        System.out.println("账号: " + email);
    }
}
