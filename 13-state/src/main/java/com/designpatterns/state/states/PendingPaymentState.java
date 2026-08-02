package com.designpatterns.state.states;

import com.designpatterns.state.AbstractOrderState;
import com.designpatterns.state.Order;

/**
 * 待支付状态
 * 可以：支付、取消
 * 不可以：发货、确认收货、退款、评价（使用基类默认实现）
 */
public class PendingPaymentState extends AbstractOrderState {
    
    @Override
    public void pay(Order order) {
        System.out.println("✅ 支付成功！订单金额：" + order.getAmount());
        order.setState(new PaidState());
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("✅ 取消订单成功");
        order.setState(new CancelledState());
    }
    
    @Override
    public String getStateName() {
        return "待支付";
    }
    
    // ship(), deliver(), refund(), review() 使用基类的默认实现（拒绝操作）
}
