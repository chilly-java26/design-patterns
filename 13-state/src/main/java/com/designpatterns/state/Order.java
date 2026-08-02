package com.designpatterns.state;

import com.designpatterns.state.states.PendingPaymentState;

/**
 * 订单类（上下文）
 * 维护当前状态，并将操作委托给状态对象处理
 */
public class Order {
    
    private OrderState state;
    private String orderId;
    private double amount;
    
    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        // 初始状态：待支付
        this.state = new PendingPaymentState();
    }
    
    /**
     * 支付订单（委托给状态对象）
     */
    public void pay() {
        state.pay(this);
    }
    
    /**
     * 发货（委托给状态对象）
     */
    public void ship() {
        state.ship(this);
    }
    
    /**
     * 确认收货（委托给状态对象）
     */
    public void deliver() {
        state.deliver(this);
    }
    
    /**
     * 取消订单（委托给状态对象）
     */
    public void cancel() {
        state.cancel(this);
    }
    
    /**
     * 退款（委托给状态对象）
     */
    public void refund() {
        state.refund(this);
    }
    
    /**
     * 评价订单（委托给状态对象）
     */
    public void review() {
        state.review(this);
    }
    
    /**
     * 设置状态（由状态对象调用，实现状态转换）
     */
    public void setState(OrderState state) {
        String oldState = this.state.getStateName();
        this.state = state;
        System.out.println("状态变更: " + oldState + " -> " + state.getStateName());
    }
    
    /**
     * 获取当前状态
     */
    public OrderState getState() {
        return state;
    }
    
    /**
     * 获取订单号
     */
    public String getOrderId() {
        return orderId;
    }
    
    /**
     * 获取订单金额
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * 打印订单信息
     */
    public void printInfo() {
        System.out.println("订单号: " + orderId + ", 金额: " + amount + ", 当前状态: " + state.getStateName());
    }
}
