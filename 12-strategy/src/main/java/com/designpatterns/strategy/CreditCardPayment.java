package com.designpatterns.strategy;

/**
 * 具体策略 - 信用卡支付
 */
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        System.out.println("使用信用卡支付 " + amount + " 元");
        System.out.println("卡号: " + maskCardNumber(cardNumber));
    }

    private String maskCardNumber(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}
