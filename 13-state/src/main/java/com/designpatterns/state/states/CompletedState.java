package com.designpatterns.state.states;

import com.designpatterns.state.AbstractOrderState;
import com.designpatterns.state.Order;

/**
 * 已完成状态
 * 可以：评价
 * 不可以：支付、发货、确认收货、取消、退款（使用基类默认实现）
 */
public class CompletedState extends AbstractOrderState {
    
    @Override
    public void review(Order order) {
        System.out.println("✅ 评价成功：非常满意！⭐⭐⭐⭐⭐");
        System.out.println("订单流程结束");
    }
    
    @Override
    public String getStateName() {
        return "已完成";
    }
    
    // pay(), ship(), deliver(), cancel(), refund() 使用基类的默认实现（拒绝操作）
}
