package com.designpatterns.state;

/**
 * 抽象状态基类
 * 提供所有操作的默认实现（拒绝操作），子类只需重写支持的操作
 */
public abstract class AbstractOrderState implements OrderState {
    
    /**
     * 默认实现：拒绝支付操作
     */
    @Override
    public void pay(Order order) {
        System.out.println("❌ 当前状态[" + getStateName() + "]不支持支付操作");
    }
    
    /**
     * 默认实现：拒绝发货操作
     */
    @Override
    public void ship(Order order) {
        System.out.println("❌ 当前状态[" + getStateName() + "]不支持发货操作");
    }
    
    /**
     * 默认实现：拒绝确认收货操作
     */
    @Override
    public void deliver(Order order) {
        System.out.println("❌ 当前状态[" + getStateName() + "]不支持确认收货操作");
    }
    
    /**
     * 默认实现：拒绝取消操作
     */
    @Override
    public void cancel(Order order) {
        System.out.println("❌ 当前状态[" + getStateName() + "]不支持取消操作");
    }
    
    /**
     * 默认实现：拒绝退款操作
     */
    @Override
    public void refund(Order order) {
        System.out.println("❌ 当前状态[" + getStateName() + "]不支持退款操作");
    }
    
    /**
     * 默认实现：拒绝评价操作
     */
    @Override
    public void review(Order order) {
        System.out.println("❌ 当前状态[" + getStateName() + "]不支持评价操作");
    }
    
    /**
     * 子类必须实现：返回状态名称
     */
    @Override
    public abstract String getStateName();
}
