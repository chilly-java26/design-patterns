package com.designpatterns.state;

/**
 * 状态模式演示：订单状态流转
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== 状态模式演示：订单状态流转 ===\n");
        
        // 场景1：正常下单流程
        scenario1_NormalFlow();
        
        printSeparator();
        
        // 场景2：未支付直接取消
        scenario2_CancelBeforePayment();
        
        printSeparator();
        
        // 场景3：支付后申请退款
        scenario3_RefundAfterPayment();
        
        printSeparator();
        
        // 场景4：各种非法操作演示
        scenario4_InvalidOperations();
    }
    
    /**
     * 打印分隔线
     */
    private static void printSeparator() {
        System.out.println("\n" + "==================================================\n");
    }
    
    /**
     * 场景1：正常下单流程
     * 待支付 -> 已支付 -> 配送中 -> 已完成 -> 评价
     */
    private static void scenario1_NormalFlow() {
        System.out.println("【场景1：正常下单流程】");
        Order order = new Order("ORD-20260802-001", 299.99);
        System.out.println("创建订单: " + order.getOrderId() + ", 金额: " + order.getAmount());
        System.out.println("当前状态: " + order.getState().getStateName());
        
        System.out.println("\n执行操作: 支付订单");
        order.pay();
        
        System.out.println("\n执行操作: 发货");
        order.ship();
        
        System.out.println("\n执行操作: 确认收货");
        order.deliver();
        
        System.out.println("\n执行操作: 评价订单");
        order.review();
    }
    
    /**
     * 场景2：未支付直接取消
     */
    private static void scenario2_CancelBeforePayment() {
        System.out.println("【场景2：未支付直接取消】");
        Order order = new Order("ORD-20260802-002", 199.50);
        System.out.println("创建订单: " + order.getOrderId() + ", 金额: " + order.getAmount());
        System.out.println("当前状态: " + order.getState().getStateName());
        
        System.out.println("\n执行操作: 取消订单");
        order.cancel();
        
        System.out.println("\n尝试非法操作: 支付已取消的订单");
        order.pay();
    }
    
    /**
     * 场景3：支付后申请退款
     */
    private static void scenario3_RefundAfterPayment() {
        System.out.println("【场景3：支付后申请退款】");
        Order order = new Order("ORD-20260802-003", 88.88);
        System.out.println("创建订单: " + order.getOrderId() + ", 金额: " + order.getAmount());
        System.out.println("当前状态: " + order.getState().getStateName());
        
        System.out.println("\n执行操作: 支付订单");
        order.pay();
        
        System.out.println("\n执行操作: 申请退款");
        order.refund();
    }
    
    /**
     * 场景4：各种非法操作演示
     */
    private static void scenario4_InvalidOperations() {
        System.out.println("【场景4：各种非法操作演示】");
        Order order = new Order("ORD-20260802-004", 999.00);
        System.out.println("创建订单: " + order.getOrderId() + ", 金额: " + order.getAmount());
        
        System.out.println("\n尝试在【待支付】状态下发货:");
        order.ship();
        
        System.out.println("\n尝试在【待支付】状态下确认收货:");
        order.deliver();
        
        System.out.println("\n支付订单后...");
        order.pay();
        
        System.out.println("\n尝试在【已支付】状态下重复支付:");
        order.pay();
        
        System.out.println("\n尝试在【已支付】状态下确认收货:");
        order.deliver();
        
        System.out.println("\n正确发货:");
        order.ship();
        
        System.out.println("\n尝试在【配送中】状态下重复发货:");
        order.ship();
        
        System.out.println("\n正确确认收货:");
        order.deliver();
        
        System.out.println("\n尝试在【已完成】状态下取消订单:");
        order.cancel();
        
        System.out.println("\n正确评价订单:");
        order.review();
    }
}
