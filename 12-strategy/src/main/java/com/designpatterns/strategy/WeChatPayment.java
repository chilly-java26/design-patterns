package com.designpatterns.strategy;

/**
 * 具体策略 - 微信支付
 */
public class WeChatPayment implements PaymentStrategy {
    private String openId;

    public WeChatPayment(String openId) {
        this.openId = openId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("使用微信支付 " + amount + " 元");
        System.out.println("OpenID: " + openId);
    }
}
