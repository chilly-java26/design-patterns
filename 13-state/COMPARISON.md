# 状态模式 vs 传统 if-else 对比

## 场景：订单的支付操作

### 传统 if-else 方案

```java
public class OrderWithIfElse {
    private String state;  // "PENDING", "PAID", "SHIPPING", "COMPLETED", "CANCELLED"
    private String orderId;
    private double amount;
    
    // 支付操作
    public void pay() {
        if (state.equals("PENDING")) {
            System.out.println("支付成功！");
            state = "PAID";
        } else if (state.equals("PAID")) {
            System.out.println("❌ 订单已经支付，无法重复支付");
        } else if (state.equals("SHIPPING")) {
            System.out.println("❌ 订单已经支付，无法重复支付");
        } else if (state.equals("COMPLETED")) {
            System.out.println("❌ 订单已完成，无法支付");
        } else if (state.equals("CANCELLED")) {
            System.out.println("❌ 订单已取消，无法支付");
        }
    }
    
    // 发货操作
    public void ship() {
        if (state.equals("PENDING")) {
            System.out.println("❌ 订单还未支付，无法发货");
        } else if (state.equals("PAID")) {
            System.out.println("订单已发货");
            state = "SHIPPING";
        } else if (state.equals("SHIPPING")) {
            System.out.println("❌ 订单已经发货，无法重复发货");
        } else if (state.equals("COMPLETED")) {
            System.out.println("❌ 订单已完成，无法发货");
        } else if (state.equals("CANCELLED")) {
            System.out.println("❌ 订单已取消，无法发货");
        }
    }
    
    // 确认收货操作
    public void deliver() {
        if (state.equals("PENDING")) {
            System.out.println("❌ 订单还未支付，无法确认收货");
        } else if (state.equals("PAID")) {
            System.out.println("❌ 订单还未发货，无法确认收货");
        } else if (state.equals("SHIPPING")) {
            System.out.println("订单已签收");
            state = "COMPLETED";
        } else if (state.equals("COMPLETED")) {
            System.out.println("❌ 订单已完成，无法重复确认收货");
        } else if (state.equals("CANCELLED")) {
            System.out.println("❌ 订单已取消，无法确认收货");
        }
    }
    
    // 取消订单操作
    public void cancel() {
        if (state.equals("PENDING")) {
            System.out.println("取消订单成功");
            state = "CANCELLED";
        } else if (state.equals("PAID")) {
            System.out.println("❌ 订单已支付，无法直接取消，请申请退款");
        } else if (state.equals("SHIPPING")) {
            System.out.println("❌ 订单正在配送中，无法取消");
        } else if (state.equals("COMPLETED")) {
            System.out.println("❌ 订单已完成，无法取消");
        } else if (state.equals("CANCELLED")) {
            System.out.println("❌ 订单已经取消，无法重复取消");
        }
    }
    
    // 退款操作
    public void refund() {
        if (state.equals("PENDING")) {
            System.out.println("❌ 订单还未支付，无需退款");
        } else if (state.equals("PAID")) {
            System.out.println("退款成功");
            state = "CANCELLED";
        } else if (state.equals("SHIPPING")) {
            System.out.println("拒收成功，订单将退回");
            state = "CANCELLED";
        } else if (state.equals("COMPLETED")) {
            System.out.println("❌ 订单已完成，如需退货请联系客服");
        } else if (state.equals("CANCELLED")) {
            System.out.println("❌ 订单已取消，无需退款");
        }
    }
    
    // 评价订单操作
    public void review() {
        if (state.equals("PENDING")) {
            System.out.println("❌ 订单还未完成，无法评价");
        } else if (state.equals("PAID")) {
            System.out.println("❌ 订单还未完成，无法评价");
        } else if (state.equals("SHIPPING")) {
            System.out.println("❌ 订单还未完成，无法评价");
        } else if (state.equals("COMPLETED")) {
            System.out.println("评价成功");
        } else if (state.equals("CANCELLED")) {
            System.out.println("❌ 订单已取消，无法评价");
        }
    }
}
```

### 状态模式方案

```java
// 状态接口
public interface OrderState {
    void pay(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
    void refund(Order order);
    void review(Order order);
    String getStateName();
}

// 订单类（上下文）
public class Order {
    private OrderState state;
    private String orderId;
    private double amount;
    
    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.state = new PendingPaymentState();  // 初始状态
    }
    
    // 简单委托给状态对象
    public void pay() { state.pay(this); }
    public void ship() { state.ship(this); }
    public void deliver() { state.deliver(this); }
    public void cancel() { state.cancel(this); }
    public void refund() { state.refund(this); }
    public void review() { state.review(this); }
    
    public void setState(OrderState state) {
        this.state = state;
    }
    
    // getters...
}

// 具体状态：待支付
public class PendingPaymentState implements OrderState {
    @Override
    public void pay(Order order) {
        System.out.println("✅ 支付成功！");
        order.setState(new PaidState());  // 状态转换
    }
    
    @Override
    public void ship(Order order) {
        System.out.println("❌ 订单还未支付，无法发货");
    }
    
    @Override
    public void deliver(Order order) {
        System.out.println("❌ 订单还未支付，无法确认收货");
    }
    
    @Override
    public void cancel(Order order) {
        System.out.println("✅ 取消订单成功");
        order.setState(new CancelledState());
    }
    
    @Override
    public void refund(Order order) {
        System.out.println("❌ 订单还未支付，无需退款");
    }
    
    @Override
    public void review(Order order) {
        System.out.println("❌ 订单还未完成，无法评价");
    }
    
    @Override
    public String getStateName() {
        return "待支付";
    }
}

// 其他状态类似实现...
```

## 对比分析

### 1. 代码行数对比

| 方案 | 文件数 | 总行数 | 平均每个方法行数 |
|------|--------|--------|----------------|
| **if-else方案** | 1个类 | ~150行 | 25行 |
| **状态模式** | 8个类 | ~250行 | 10行 |

虽然状态模式总行数更多，但**可维护性更高**。

### 2. 代码复杂度对比

#### if-else方案的问题：

```java
public void pay() {
    if (state.equals("PENDING")) {
        // 5行逻辑
    } else if (state.equals("PAID")) {
        // 3行逻辑
    } else if (state.equals("SHIPPING")) {
        // 3行逻辑
    } else if (state.equals("COMPLETED")) {
        // 3行逻辑
    } else if (state.equals("CANCELLED")) {
        // 3行逻辑
    }
}
```

**圈复杂度：6**（每个方法都有5个分支）

#### 状态模式的优势：

```java
// Order类
public void pay() {
    state.pay(this);  // 只有1行，圈复杂度为1
}

// PendingPaymentState类
public void pay(Order order) {
    System.out.println("✅ 支付成功！");
    order.setState(new PaidState());
    // 圈复杂度为1
}
```

**圈复杂度：1**（每个方法逻辑单一）

### 3. 可扩展性对比

#### 场景：新增"待评价"状态

**if-else方案：**
```
需要修改的地方：
1. 修改 pay() 方法 - 增加对"待评价"状态的判断
2. 修改 ship() 方法 - 增加对"待评价"状态的判断
3. 修改 deliver() 方法 - 改为转到"待评价"状态
4. 修改 cancel() 方法 - 增加对"待评价"状态的判断
5. 修改 refund() 方法 - 增加对"待评价"状态的判断
6. 修改 review() 方法 - 处理"待评价"状态的逻辑
```
❌ **需要修改6个方法，影响范围大**

**状态模式方案：**
```
需要做的事情：
1. 新增 PendingReviewState 类
2. 修改 ShippingState.deliver() - 转到新状态
3. 修改 PendingReviewState 实现各个操作
```
✅ **只需新增1个类，修改1个方法，符合开闭原则**

### 4. 状态转换清晰度对比

#### if-else方案：
```java
public void pay() {
    if (state.equals("PENDING")) {
        state = "PAID";  // 转换逻辑分散在各处
    }
    // ...
}

public void ship() {
    if (state.equals("PAID")) {
        state = "SHIPPING";  // 转换逻辑分散在各处
    }
    // ...
}
```
❌ **状态转换逻辑分散，难以维护**

#### 状态模式：
```java
public class PendingPaymentState implements OrderState {
    public void pay(Order order) {
        // ...
        order.setState(new PaidState());  // 转换逻辑集中在状态类内
    }
}

public class PaidState implements OrderState {
    public void ship(Order order) {
        // ...
        order.setState(new ShippingState());  // 转换逻辑集中在状态类内
    }
}
```
✅ **状态转换逻辑清晰，一目了然**

### 5. 测试复杂度对比

#### if-else方案：
```java
@Test
public void testPay() {
    Order order = new Order();
    
    // 需要测试所有状态下的pay()行为
    order.setState("PENDING");
    order.pay();  // 测试1
    
    order.setState("PAID");
    order.pay();  // 测试2
    
    order.setState("SHIPPING");
    order.pay();  // 测试3
    
    // ... 每个方法都需要测试5种状态
}
```
❌ **每个方法需要测试5种状态，5×6=30个测试用例**

#### 状态模式：
```java
@Test
public void testPendingPaymentState() {
    Order order = new Order();
    order.setState(new PendingPaymentState());
    
    // 只需要测试这个状态下的所有行为
    order.pay();     // 测试1
    order.ship();    // 测试2
    // ... 6个测试用例
}

@Test
public void testPaidState() {
    // 同样6个测试用例
}
```
✅ **每个状态独立测试，逻辑清晰，5×6=30个测试用例，但更有组织性**

### 6. 代码可读性对比

#### if-else方案：
- ❌ 每个方法都有大量条件判断
- ❌ 状态转换逻辑不清晰
- ❌ 难以快速理解状态机的完整流程

#### 状态模式：
- ✅ 每个状态类只关注自己的行为
- ✅ 状态转换一目了然
- ✅ 可以通过类结构快速理解整个状态机

## 总结

| 维度 | if-else方案 | 状态模式 | 胜者 |
|------|------------|---------|------|
| **代码行数** | 150行（1个类） | 250行（8个类） | if-else |
| **圈复杂度** | 每个方法6 | 每个方法1 | 状态模式 ✅ |
| **可扩展性** | 修改多个方法 | 新增1个类 | 状态模式 ✅ |
| **可维护性** | 分散 | 集中 | 状态模式 ✅ |
| **状态转换清晰度** | 不清晰 | 非常清晰 | 状态模式 ✅ |
| **测试组织性** | 混乱 | 清晰 | 状态模式 ✅ |
| **开闭原则** | 违反 | 符合 | 状态模式 ✅ |
| **简单性** | 简单（小项目） | 复杂（小项目） | if-else |

### 何时使用 if-else？
- ✅ 状态少于3个
- ✅ 状态逻辑简单
- ✅ 不需要频繁扩展

### 何时使用状态模式？
- ✅ 状态数量多（≥3个）
- ✅ 每个状态下的行为复杂
- ✅ 需要频繁增加新状态
- ✅ 状态转换逻辑复杂
- ✅ 需要清晰的状态机设计

## 最佳实践建议

1. **小型项目**：3个状态以下，用if-else更简单
2. **中型项目**：3-5个状态，考虑使用状态模式
3. **大型项目**：5个以上状态，强烈推荐状态模式
4. **频繁变化**：状态经常增加或修改，使用状态模式
5. **团队协作**：多人维护的代码，状态模式更清晰
