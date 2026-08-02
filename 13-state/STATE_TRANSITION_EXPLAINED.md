# 状态跳转机制详解

## 核心原理：状态对象自己控制跳转

在状态模式中，**状态跳转是由当前状态对象自己决定的**，而不是由外部控制。

## 跳转流程图解

```
┌─────────────────────────────────────────────────────────────┐
│                    用户调用                                   │
│                      order.pay()                            │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                Order类（上下文）                              │
│                                                               │
│    public void pay() {                                       │
│        state.pay(this);  // 委托给当前状态对象               │
│    }                     // 把自己(this)传过去               │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│            PendingPaymentState（当前状态）                    │
│                                                               │
│    public void pay(Order order) {                            │
│        // 1. 执行业务逻辑                                     │
│        System.out.println("支付成功");                        │
│                                                               │
│        // 2. 决定下一个状态并跳转                            │
│        order.setState(new PaidState());  // 🔑 关键！        │
│    }                                                          │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                Order类（上下文）                              │
│                                                               │
│    public void setState(OrderState newState) {               │
│        this.state = newState;  // 替换状态对象               │
│        System.out.println("状态变更: 待支付 -> 已支付");      │
│    }                                                          │
└─────────────────────────────────────────────────────────────┘
```

## 详细代码示例

### 示例1：支付操作（待支付 → 已支付）

```java
// 1. 用户调用
Order order = new Order("ORD-001", 299.99);
order.pay();  // 用户调用支付

// 2. Order类委托给状态对象
public class Order {
    private OrderState state = new PendingPaymentState();  // 当前是待支付状态
    
    public void pay() {
        state.pay(this);  // 🔹 把自己传给状态对象
    }
    
    public void setState(OrderState newState) {
        this.state = newState;  // 🔹 更新状态对象引用
    }
}

// 3. PendingPaymentState处理并跳转
public class PendingPaymentState implements OrderState {
    @Override
    public void pay(Order order) {
        // 步骤1：执行业务逻辑
        System.out.println("✅ 支付成功！订单金额：" + order.getAmount());
        
        // 步骤2：决定跳转到哪个状态
        order.setState(new PaidState());  // 🔑 调用Order的setState方法
    }
}

// 4. 状态已更新
// order.state 现在指向 PaidState 对象
```

### 示例2：发货操作（已支付 → 配送中）

```java
// 1. 继续调用
order.ship();  // 用户调用发货

// 2. Order类委托（此时state已经是PaidState了）
public class Order {
    private OrderState state;  // 现在是 PaidState 对象
    
    public void ship() {
        state.ship(this);  // 委托给 PaidState
    }
}

// 3. PaidState处理并跳转
public class PaidState implements OrderState {
    @Override
    public void ship(Order order) {
        // 步骤1：执行业务逻辑
        System.out.println("✅ 订单已发货，物流单号：SF1234567890");
        
        // 步骤2：跳转到下一个状态
        order.setState(new ShippingState());  // 🔑 跳转到配送中
    }
}

// 4. 状态已更新
// order.state 现在指向 ShippingState 对象
```

## 核心机制：回调模式

状态跳转使用了**回调模式**：

```java
// Order把自己(this)传给状态对象
state.pay(this);

// 状态对象通过这个引用调用Order的setState方法
public void pay(Order order) {
    // order就是传入的Order对象
    order.setState(new PaidState());  // 回调Order的方法
}
```

## 对象引用变化示意图

### 初始状态

```
Order对象
┌─────────────────────┐
│ orderId: "ORD-001"  │
│ amount: 299.99      │
│ state: ───────────┐ │
└───────────────────┼─┘
                    │
                    ▼
        ┌───────────────────────┐
        │ PendingPaymentState   │
        │ (待支付状态对象)       │
        └───────────────────────┘
```

### 调用 order.pay() 后

```
Order对象
┌─────────────────────┐
│ orderId: "ORD-001"  │
│ amount: 299.99      │
│ state: ───────────┐ │  (引用被替换了！)
└───────────────────┼─┘
                    │
                    ▼
        ┌───────────────────────┐
        │ PaidState             │
        │ (已支付状态对象)       │
        └───────────────────────┘

        (旧的对象被垃圾回收)
        ┌───────────────────────┐
        │ PendingPaymentState   │  (没有引用了)
        └───────────────────────┘
```

## 完整调用链

让我们追踪一次完整的状态跳转：

```java
// ======= 代码执行流程 =======

// 1. 用户代码
Order order = new Order("ORD-001", 299.99);
// 此时：order.state = new PendingPaymentState()

order.pay();

// 2. 进入Order.pay()
public void pay() {
    state.pay(this);  // state = PendingPaymentState对象
                      // this = order对象自己
}

// 3. 进入PendingPaymentState.pay(order)
public void pay(Order order) {
    // order参数 = 外面的order对象
    System.out.println("✅ 支付成功！");
    
    // 🔑 关键一步：调用order.setState()
    order.setState(new PaidState());
}

// 4. 进入Order.setState(newState)
public void setState(OrderState newState) {
    // newState = 新创建的PaidState对象
    
    String oldState = this.state.getStateName();  // "待支付"
    this.state = newState;  // 🔑 替换引用！
    String newStateName = newState.getStateName();  // "已支付"
    
    System.out.println("状态变更: " + oldState + " -> " + newStateName);
}

// 5. 返回到用户代码
// 此时：order.state = PaidState对象（已经变了！）
```

## 为什么状态对象要持有Order的引用？

```java
public void pay(Order order) {  // 👈 为什么需要这个参数？
    // 原因1：可以访问Order的数据
    double amount = order.getAmount();  // 获取金额
    String orderId = order.getOrderId();  // 获取订单号
    
    // 原因2：可以调用Order的setState方法来跳转状态
    order.setState(new PaidState());  // 🔑 这是核心！
}
```

## 状态跳转的三种方式对比

### 方式1：状态对象内部控制（当前实现，推荐✅）

```java
public class PendingPaymentState implements OrderState {
    public void pay(Order order) {
        System.out.println("支付成功");
        order.setState(new PaidState());  // 状态自己决定跳转
    }
}
```

**优点**：
- ✅ 状态转换逻辑集中在状态类内部
- ✅ 符合单一职责原则
- ✅ 易于理解和维护

**缺点**：
- ❌ 状态类之间有依赖（需要知道其他状态类）

### 方式2：上下文控制（不推荐❌）

```java
public class Order {
    public void pay() {
        state.pay(this);
        
        // ❌ 在这里判断状态并跳转
        if (state instanceof PendingPaymentState) {
            this.state = new PaidState();
        }
    }
}
```

**缺点**：
- ❌ 违反开闭原则
- ❌ Order类需要知道所有状态类
- ❌ 状态转换逻辑分散

### 方式3：返回值控制

```java
public interface OrderState {
    OrderState pay(Order order);  // 返回下一个状态
}

public class PendingPaymentState implements OrderState {
    public OrderState pay(Order order) {
        System.out.println("支付成功");
        return new PaidState();  // 返回新状态
    }
}

public class Order {
    public void pay() {
        OrderState newState = state.pay(this);
        if (newState != null) {
            this.state = newState;  // 更新状态
        }
    }
}
```

**优点**：
- ✅ 状态转换显式返回，更明确

**缺点**：
- ❌ 需要修改所有状态方法的返回值
- ❌ Order需要处理返回值

## 实际运行示例

```java
public static void main(String[] args) {
    Order order = new Order("ORD-001", 299.99);
    
    // 输出：当前状态: 待支付
    System.out.println("当前状态: " + order.getState().getStateName());
    
    // 调用支付
    order.pay();
    // 输出：
    // ✅ 支付成功！订单金额：299.99
    // 状态变更: 待支付 -> 已支付
    
    // 输出：当前状态: 已支付
    System.out.println("当前状态: " + order.getState().getStateName());
    
    // 调用发货
    order.ship();
    // 输出：
    // ✅ 订单已发货，物流单号：SF1234567890
    // 状态变更: 已支付 -> 配送中
    
    // 输出：当前状态: 配送中
    System.out.println("当前状态: " + order.getState().getStateName());
}
```

## 关键要点总结

1. **谁控制跳转**：状态对象自己决定跳转到哪个状态
2. **如何跳转**：通过调用 `order.setState(new NextState())`
3. **为什么需要order参数**：状态对象需要这个引用来调用setState方法
4. **状态存储在哪**：Order对象的state字段（是一个对象引用）
5. **跳转时发生什么**：state引用从旧状态对象切换到新状态对象

## 类比理解

把状态模式想象成**交接班**：

```
当前值班员（PendingPaymentState）：
"好的，支付任务完成了，下一班应该是发货组负责，
 让我叫发货组的同事（PaidState）来接班。"

order.setState(new PaidState());  // 交接班

新值班员（PaidState）：
"收到！我是发货组，现在由我负责这个订单。"
```

每个状态对象就像一个值班员，完成自己的任务后，主动叫下一个值班员来接班。Order对象就像一个工作台，永远由当前值班员（state）来处理工作。
