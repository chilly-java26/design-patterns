# 状态模式 (State Pattern)

## 一句话本质
**将对象的状态封装成独立的类，让对象在不同状态下的行为由状态类来处理，从而避免大量的 if-else 或 switch-case 条件判断。**

## 核心思想
对象的行为取决于它的状态，当状态改变时，行为也跟着改变。

## 设计场景：订单状态流转系统

### 业务场景
一个电商订单从创建到完成需要经历多个状态：

```
待支付 ──支付──> 已支付 ──发货──> 配送中 ──确认收货──> 已完成 ──评价──> (结束)
  │                │                                │
  │                │                                │
  └───取消────> 已取消                             │
                   ▲                                │
                   │                                │
                   └────────────退款────────────────┘
```

### 传统if-else方案的问题

```java
public class Order {
    private String state;  // "PENDING", "PAID", "SHIPPING", "COMPLETED", "CANCELLED"
    
    public void pay() {
        if (state.equals("PENDING")) {
            // 支付逻辑
            state = "PAID";
        } else if (state.equals("PAID")) {
            System.out.println("已经支付过了");
        } else if (state.equals("SHIPPING")) {
            System.out.println("订单正在配送中，无法支付");
        } else if (state.equals("COMPLETED")) {
            System.out.println("订单已完成，无法支付");
        } else if (state.equals("CANCELLED")) {
            System.out.println("订单已取消，无法支付");
        }
    }
    
    public void ship() { /* 同样的if-else */ }
    public void deliver() { /* 同样的if-else */ }
    public void cancel() { /* 同样的if-else */ }
    public void refund() { /* 同样的if-else */ }
    public void review() { /* 同样的if-else */ }
}
```

**问题：**
- ❌ 每个方法都有大量重复的状态判断
- ❌ 增加新状态需要修改所有方法
- ❌ 状态转换逻辑分散在各处，难以维护
- ❌ 违反开闭原则

## 状态模式解决方案

### 核心设计

```
┌───────────────┐
│ Order         │  (上下文)
│ - state       │◆────────┐
│ + setState()  │         │
│ + pay()       │         │
│ + ship()      │         │
│ + cancel()    │         │
└───────────────┘         │
                          │
                          ▼
            ┌──────────────────────┐
            │ OrderState           │  (状态接口)
            │ + pay(order)         │
            │ + ship(order)        │
            │ + deliver(order)     │
            │ + cancel(order)      │
            │ + refund(order)      │
            │ + review(order)      │
            └──────────────────────┘
                       △
       ┌───────────────┼───────────────┬───────────────┬─────────────┐
       │               │               │               │             │
┌──────────────┐ ┌──────────┐ ┌──────────┐ ┌────────────┐ ┌──────────┐
│ PendingState │ │PaidState │ │Shipping  │ │Completed   │ │Cancelled │
│              │ │          │ │State     │ │State       │ │State     │
└──────────────┘ └──────────┘ └──────────┘ └────────────┘ └──────────┘
```

### 项目结构

```
src/main/java/com/designpatterns/state/
├── Main.java                        # 演示入口
├── Order.java                       # 上下文（订单）
├── OrderState.java                  # 状态接口
└── states/                          # 具体状态实现
    ├── PendingPaymentState.java    # 待支付状态
    ├── PaidState.java               # 已支付状态
    ├── ShippingState.java           # 配送中状态
    ├── CompletedState.java          # 已完成状态
    └── CancelledState.java          # 已取消状态
```

## 核心代码

### 状态接口

```java
public interface OrderState {
    void pay(Order order);      // 支付
    void ship(Order order);     // 发货
    void deliver(Order order);  // 确认收货
    void cancel(Order order);   // 取消订单
    void refund(Order order);   // 退款
    void review(Order order);   // 评价
    String getStateName();      // 获取状态名称
}
```

### 订单类（上下文）

```java
public class Order {
    private OrderState state;
    private String orderId;
    private double amount;
    
    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.state = new PendingPaymentState();  // 初始状态
    }
    
    // 委托给状态对象处理
    public void pay() { state.pay(this); }
    public void ship() { state.ship(this); }
    public void deliver() { state.deliver(this); }
    public void cancel() { state.cancel(this); }
    public void refund() { state.refund(this); }
    public void review() { state.review(this); }
    
    // 状态切换由状态对象控制
    public void setState(OrderState state) {
        this.state = state;
    }
}
```

### 具体状态示例

```java
// 待支付状态
public class PendingPaymentState implements OrderState {
    @Override
    public void pay(Order order) {
        System.out.println("支付成功！订单金额：" + order.getAmount());
        order.setState(new PaidState());  // 切换到已支付状态
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("取消订单成功");
        order.setState(new CancelledState());
    }
    
    @Override
    public void ship(Order order) {
        System.out.println("❌ 订单还未支付，无法发货");
    }
    
    // 其他操作类似...
}
```

## 优势对比

### 代码复杂度对比

| 方案 | 每个方法的if-else数量 | 总行数 | 新增状态成本 |
|------|---------------------|--------|-------------|
| **传统if-else** | 5个判断 × 6个方法 = 30个if | ~150行 | 修改所有方法 |
| **状态模式** | 0个 | ~180行 | 只需增加1个状态类 |

### 可维护性对比

| 维度 | 传统方案 | 状态模式 |
|------|---------|---------|
| 状态逻辑位置 | 分散在各方法 | 集中在状态类 ❌ |
| 新增状态 | 修改所有方法 | 新增一个类 ✅ |
| 状态转换清晰度 | 不明确 | 非常清晰 ✅ |
| 违反开闭原则 | 是 | 否 ✅ |

## 运行示例

### 基础演示

```bash
cd 13-state
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.state.Main"
```

### 状态跳转机制详细演示

如果你想深入理解状态是如何跳转的，运行这个详细演示：

```bash
mvn exec:java -Dexec.mainClass="com.designpatterns.state.TransitionDemo"
```

这个演示会显示每一步的状态对象变化，帮助你理解状态跳转的内部机制。

## 预期输出

```
=== 状态模式演示：订单状态流转 ===

【场景1：正常下单流程】
创建订单: ORD-20260802-001, 金额: 299.99
当前状态: 待支付

执行操作: 支付订单
✅ 支付成功！订单金额：299.99
状态变更: 待支付 -> 已支付

执行操作: 发货
✅ 订单已发货，物流单号：SF1234567890
状态变更: 已支付 -> 配送中

执行操作: 确认收货
✅ 订单已签收，感谢您的购买
状态变更: 配送中 -> 已完成

执行操作: 评价订单
✅ 评价成功：非常满意！
订单流程结束

---

【场景2：未支付直接取消】
创建订单: ORD-20260802-002, 金额: 199.5
当前状态: 待支付

执行操作: 取消订单
✅ 取消订单成功
状态变更: 待支付 -> 已取消

尝试非法操作: 支付已取消的订单
❌ 订单已取消，无法支付

---

【场景3：支付后申请退款】
创建订单: ORD-20260802-003, 金额: 88.88
当前状态: 待支付

执行操作: 支付订单
✅ 支付成功！订单金额：88.88
状态变更: 待支付 -> 已支付

执行操作: 申请退款
✅ 退款成功，金额：88.88 将在3-5个工作日内退回
状态变更: 已支付 -> 已取消

---

【场景4：各种非法操作演示】
创建订单: ORD-20260802-004, 金额: 999.0

尝试在【待支付】状态下发货:
❌ 订单还未支付，无法发货

尝试在【待支付】状态下确认收货:
❌ 订单还未支付，无法确认收货

支付订单后...

尝试在【已支付】状态下重复支付:
❌ 订单已经支付，无法重复支付

尝试在【已支付】状态下确认收货:
❌ 订单还未发货，无法确认收货
```

## 关键设计点

### 1. 状态封装

每个状态是独立的类，封装该状态下的所有行为：

```java
public class PaidState implements OrderState {
    // 已支付状态下的所有行为逻辑都在这里
    // 清晰、集中、易于维护
}
```

### 2. 状态转换

状态转换由状态对象自己控制，而不是由外部控制：

```java
public void pay(Order order) {
    // 处理支付逻辑
    System.out.println("支付成功");
    // 自己决定下一个状态
    order.setState(new PaidState());
}
```

### 3. 上下文委托

Order类不知道具体状态，只是简单委托：

```java
public void pay() {
    state.pay(this);  // 委托给当前状态处理
}
```

## 适用场景

### 何时使用状态模式？

1. ✅ **对象行为随状态改变而改变**：订单、工作流、游戏角色
2. ✅ **大量条件判断与状态相关**：避免复杂的if-else
3. ✅ **状态转换逻辑复杂**：状态之间有明确的转换规则
4. ✅ **需要清晰的状态机**：状态和转换一目了然

### 实际应用场景

- **订单系统**：待支付 → 已支付 → 配送中 → 已完成（本例）
- **工作流引擎**：草稿 → 审批中 → 已通过 → 已归档
- **TCP连接**：CLOSED → LISTEN → SYN_SENT → ESTABLISHED
- **游戏角色**：正常 → 中毒 → 冰冻 → 眩晕
- **电梯系统**：停止 → 上升 → 下降 → 维护
- **线程状态**：NEW → RUNNABLE → BLOCKED → TERMINATED

## 与其他模式的区别

### 状态模式 vs 策略模式

| 维度 | 状态模式 | 策略模式 |
|------|---------|---------|
| **目的** | 改变对象的内在状态和行为 | 改变对象的算法或策略 |
| **状态/策略关系** | 状态之间有转换关系 | 策略之间互相独立 |
| **谁控制切换** | 状态对象自己控制 | 客户端主动选择 |
| **是否感知彼此** | 状态互相了解（转换规则） | 策略互不了解 |
| **典型场景** | 订单流转、工作流 | 支付方式、排序算法 |

**类比：**
- **状态模式**：人的一生（婴儿 → 儿童 → 青年 → 中年 → 老年），自动演变
- **策略模式**：出行方式（开车、坐地铁、骑车），主动选择

### 状态模式 vs 责任链模式

| 维度 | 状态模式 | 责任链模式 |
|------|---------|-----------|
| **处理方式** | 只有一个状态处理 | 多个处理器依次处理 |
| **对象关系** | 状态之间转换 | 处理器之间传递 |

## 优点

✅ **消除条件分支**：不需要大量if-else判断  
✅ **符合开闭原则**：新增状态不需要修改现有代码  
✅ **单一职责**：每个状态类只负责一种状态的行为  
✅ **状态转换明确**：状态切换逻辑清晰可见  
✅ **易于维护**：状态逻辑集中在各自的类中

## 缺点

❌ **类数量增加**：每个状态都需要一个类  
❌ **状态类相互依赖**：状态之间需要知道彼此（转换规则）  
❌ **过度设计**：简单的状态机用if-else更简单

## 最佳实践

### 1. 使用枚举优化状态管理

```java
public enum OrderStateEnum {
    PENDING(PendingPaymentState.class),
    PAID(PaidState.class),
    SHIPPING(ShippingState.class),
    COMPLETED(CompletedState.class),
    CANCELLED(CancelledState.class);
    
    private Class<? extends OrderState> stateClass;
    // ... 工厂方法
}
```

### 2. 状态缓存（享元模式）

```java
public class StateFactory {
    private static Map<Class, OrderState> cache = new HashMap<>();
    
    public static OrderState getState(Class<? extends OrderState> clazz) {
        return cache.computeIfAbsent(clazz, k -> {
            try {
                return k.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
```

### 3. 状态转换表

```java
public class StateTransitionTable {
    private static Map<OrderState, Map<String, OrderState>> transitions;
    
    static {
        // 定义状态转换规则
        transitions = new HashMap<>();
        Map<String, OrderState> pendingTransitions = new HashMap<>();
        pendingTransitions.put("pay", new PaidState());
        pendingTransitions.put("cancel", new CancelledState());
        transitions.put(new PendingPaymentState(), pendingTransitions);
        // ...
    }
}
```

## 扩展思考

1. 如何将状态转换规则配置化（数据库或配置文件）？
2. 如何为状态转换添加日志和监控？
3. 如何处理状态的并发访问问题？
4. 如何结合数据库持久化订单状态？
5. 状态模式如何与事件驱动架构结合？

## 学习要点

1. ✅ 理解状态模式的本质：将状态封装成类
2. ✅ 区分状态模式和策略模式的区别
3. ✅ 掌握状态转换的控制方式
4. ✅ 识别适合使用状态模式的场景
5. ✅ 避免过度设计（简单状态机用if-else即可）

## 参考资料

- 《设计模式：可复用面向对象软件的基础》- GoF
- 《Head First 设计模式》- State Pattern章节
