package com.designpatterns.state;

/**
 * 状态跳转机制演示
 * 这个类用详细的日志来展示状态是如何跳转的
 */
public class TransitionDemo {
    
    private static final String SEPARATOR = "==================================================";
    
    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("状态跳转机制详细演示");
        System.out.println(SEPARATOR);
        System.out.println();
        
        // 创建订单
        System.out.println("【步骤1】创建订单");
        Order order = new Order("ORD-DEMO-001", 99.99);
        System.out.println("✓ 订单创建完成");
        System.out.println("✓ 初始状态: " + order.getState().getStateName());
        System.out.println("✓ Order.state对象 = " + order.getState().getClass().getSimpleName());
        
        System.out.println();
        System.out.println(SEPARATOR);
        
        // 第一次状态跳转：待支付 -> 已支付
        System.out.println();
        System.out.println("【步骤2】调用 order.pay()");
        System.out.println("→ Order类将调用委托给当前状态对象");
        System.out.println("→ 当前状态对象: " + order.getState().getClass().getSimpleName());
        System.out.println();
        System.out.println("执行中...");
        order.pay();
        System.out.println();
        System.out.println("✓ pay()执行完毕");
        System.out.println("✓ 当前状态: " + order.getState().getStateName());
        System.out.println("✓ Order.state对象 = " + order.getState().getClass().getSimpleName());
        System.out.println("✓ 状态对象引用已从 PendingPaymentState 切换到 PaidState");
        
        System.out.println();
        System.out.println(SEPARATOR);
        
        // 第二次状态跳转：已支付 -> 配送中
        System.out.println();
        System.out.println("【步骤3】调用 order.ship()");
        System.out.println("→ Order类将调用委托给当前状态对象");
        System.out.println("→ 当前状态对象: " + order.getState().getClass().getSimpleName());
        System.out.println();
        System.out.println("执行中...");
        order.ship();
        System.out.println();
        System.out.println("✓ ship()执行完毕");
        System.out.println("✓ 当前状态: " + order.getState().getStateName());
        System.out.println("✓ Order.state对象 = " + order.getState().getClass().getSimpleName());
        System.out.println("✓ 状态对象引用已从 PaidState 切换到 ShippingState");
        
        System.out.println();
        System.out.println(SEPARATOR);
        
        // 第三次状态跳转：配送中 -> 已完成
        System.out.println();
        System.out.println("【步骤4】调用 order.deliver()");
        System.out.println("→ Order类将调用委托给当前状态对象");
        System.out.println("→ 当前状态对象: " + order.getState().getClass().getSimpleName());
        System.out.println();
        System.out.println("执行中...");
        order.deliver();
        System.out.println();
        System.out.println("✓ deliver()执行完毕");
        System.out.println("✓ 当前状态: " + order.getState().getStateName());
        System.out.println("✓ Order.state对象 = " + order.getState().getClass().getSimpleName());
        System.out.println("✓ 状态对象引用已从 ShippingState 切换到 CompletedState");
        
        System.out.println();
        System.out.println(SEPARATOR);
        
        // 演示非法操作
        System.out.println();
        System.out.println("【步骤5】尝试非法操作：在已完成状态下再次支付");
        System.out.println("→ 当前状态对象: " + order.getState().getClass().getSimpleName());
        System.out.println();
        System.out.println("执行中...");
        order.pay();
        System.out.println();
        System.out.println("✓ pay()执行完毕");
        System.out.println("✓ 当前状态: " + order.getState().getStateName());
        System.out.println("✓ Order.state对象 = " + order.getState().getClass().getSimpleName());
        System.out.println("✓ 状态未改变（CompletedState拒绝了这个操作）");
        
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("演示完成");
        System.out.println(SEPARATOR);
        
        System.out.println();
        System.out.println("【核心理解】");
        System.out.println("1. Order对象的state字段是一个【对象引用】");
        System.out.println("2. 每次调用order.pay()、order.ship()等方法时，");
        System.out.println("   都是委托给【当前state对象】处理");
        System.out.println("3. 状态对象在处理完业务逻辑后，");
        System.out.println("   通过调用 order.setState(new NextState())");
        System.out.println("   来【替换】Order.state的引用");
        System.out.println("4. 下次调用时，Order就会委托给【新的状态对象】了");
    }
}
