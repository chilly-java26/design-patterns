package com.designpatterns.state.states;

import com.designpatterns.state.AbstractOrderState;
import com.designpatterns.state.Order;

/**
 * 已支付状态
 * 可以：发货、退款
 * 不可以：支付、确认收货、取消、评价（使用基类默认实现）
 */
public class PaidState extends AbstractOrderState {
    
    @Override
    public void ship(Order order) {
        System.out.println("✅ 订单已发货，物流单号：SF1234567890");
        order.setState(new ShippingState());
    }
    
    @Override
    public void refund(Order order) {
        System.out.println("✅ 退款成功，金额：" + order.getAmount() + " 将在3-5个工作日内退回");
        order.setState(new CancelledState());
    }
    
    @Override
    public String getStateName() {
        return "已支付";
    }
    
    // pay(), deliver(), cancel(), review() 使用基类的默认实现（拒绝操作）
}
