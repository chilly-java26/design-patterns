package com.designpatterns.state;

/**
 * 订单状态接口
 * 定义订单在各个状态下可以执行的操作
 */
public interface OrderState {
    
    /**
     * 支付订单
     */
    void pay(Order order);
    
    /**
     * 发货
     */
    void ship(Order order);
    
    /**
     * 确认收货
     */
    void deliver(Order order);
    
    /**
     * 取消订单
     */
    void cancel(Order order);
    
    /**
     * 退款
     */
    void refund(Order order);
    
    /**
     * 评价订单
     */
    void review(Order order);
    
    /**
     * 获取状态名称
     */
    String getStateName();
}
