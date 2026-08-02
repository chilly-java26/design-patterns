package com.designpatterns.strategy;

/**
 * 策略模式演示
 * 
 * 场景：在线购物支付，用户可以选择不同的支付方式
 */
public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // 场景1: 使用信用卡支付
        System.out.println("场景1: 用户选择信用卡支付");
        PaymentStrategy creditCard = new CreditCardPayment("1234567812345678", "123");
        cart.setPaymentStrategy(creditCard);
        cart.checkout(299.99);

        // 场景2: 切换到支付宝支付
        System.out.println("场景2: 用户切换到支付宝支付");
        PaymentStrategy alipay = new AlipayPayment("user@example.com");
        cart.setPaymentStrategy(alipay);
        cart.checkout(199.50);

        // 场景3: 切换到微信支付
        System.out.println("场景3: 用户切换到微信支付");
        PaymentStrategy wechat = new WeChatPayment("wx_abc123456");
        cart.setPaymentStrategy(wechat);
        cart.checkout(88.88);

        // 场景4: 未设置支付策略
        System.out.println("场景4: 未选择支付方式");
        ShoppingCart newCart = new ShoppingCart();
        newCart.checkout(100.00);
    }
}
