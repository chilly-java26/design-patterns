package com.designpatterns.state.states;

import com.designpatterns.state.AbstractOrderState;
import com.designpatterns.state.Order;

/**
 * 配送中状态
 * 可以：确认收货、退款（拒收）
 * 不可以：支付、发货、取消、评价（使用基类默认实现）
 */
public class ShippingState extends AbstractOrderState {
    
    @Override
    public void deliver(Order order) {
        System.out.println("✅ 订单已签收，感谢您的购买");
        order.setState(new CompletedState());
    }
    
    @Override
    public void refund(Order order) {
        System.out.println("✅ 拒收成功，订单将退回，退款金额：" + order.getAmount());
        order.setState(new CancelledState());
    }
    
    @Override
    public String getStateName() {
        return "配送中";
    }
    
    // pay(), ship(), cancel(), review() 使用基类的默认实现（拒绝操作）
}
