# 责任链模式（Chain of Responsibility Pattern）

## 概述
责任链模式将请求的发送者和接收者解耦，让多个对象都有机会处理请求。将这些对象连成一条链，并沿着这条链传递请求，直到有对象处理它为止。

## 核心角色

### 1. Chain（责任链接口）
- 定义 `next()` 方法传递请求

### 2. Handler（抽象处理者）
- `Approver` - 定义处理请求的接口

### 3. ConcreteHandler（具体处理者）
- `Leader` - 处理1天以内的假期
- `Manager` - 处理3天以内的假期
- `Director` - 处理7天以内的假期
- `Boss` - 处理任意天数的假期

### 4. Request（请求对象）
- `LeaveRequest` - 封装请假信息

## 设计特点

### Filter风格设计
本实现采用类似 Servlet Filter 的设计：

1. **显式传递**：处理者通过调用 `chain.next()` 主动传递请求
2. **默认终止**：不调用 `next()` 则请求终止
3. **灵活控制**：每个处理者自己决定是否继续传递

### 关键代码
```java
@Override
public void next(LeaveRequest request) {
    if (currentIndex < approvers.size()) {
        Approver approver = approvers.get(currentIndex);  // 先取出
        currentIndex++;                                   // 再递增
        approver.handle(request, this);                   // 最后调用
    }
}
```

## 使用场景
- 请假审批流程
- 异常处理机制
- Servlet Filter链
- Spring Security 过滤器链
- Netty Pipeline
- 日志级别过滤

## 优点
- 降低耦合度：请求发送者不需要知道谁会处理
- 增强灵活性：可以动态添加或删除处理者
- 符合单一职责：每个处理者只处理自己的部分
- 符合开闭原则：容易扩展新的处理者

## 缺点
- 请求可能不被处理
- 性能问题：请求可能经过很多处理者
- 调试困难：不容易观察运行时特征

## 运行示例

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.designpatterns.chain.ChainDemo"
```

## 实际应用

### Java Web Filter
```java
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
    // 前置处理
    System.out.println("处理前");
    
    chain.doFilter(req, res);  // 传递给下一个
    
    // 后置处理
    System.out.println("处理后");
}
```

### Spring Interceptor
```java
public boolean preHandle(HttpServletRequest request, 
                        HttpServletResponse response, 
                        Object handler) {
    // 返回 true 继续，返回 false 终止
    return true;
}
```

### Netty Pipeline
```java
pipeline.addLast(new Handler1());
pipeline.addLast(new Handler2());
pipeline.addLast(new Handler3());
```
