# 管道模式（Pipeline Pattern）

## 概述

管道模式是一种将复杂的处理过程分解为一系列独立步骤的设计模式。每个步骤专注于单一职责，步骤之间通过上下文对象传递数据。这种模式类似于Unix管道命令，数据在各个步骤间流转并被逐步处理。

## 应用场景

本示例实现了一个用户积分处理系统，包含三个步骤：

1. **LoadUserStep**: 从数据库读取用户信息
2. **ModifyPointsStep**: 修改用户积分
3. **SaveScoreStep**: 保存积分记录到数据库

## 类图结构

```
┌─────────────────┐
│  PipelineStep   │ (接口)
│  - execute()    │
│  - getStepName()│
└────────▲────────┘
         │
         │ 实现
    ┌────┴────┬─────────────┬──────────────┐
    │         │             │              │
┌───┴────┐ ┌──┴──────┐ ┌───┴──────┐ ┌────┴────────┐
│LoadUser│ │Modify   │ │SaveScore │ │  Pipeline   │
│Step    │ │Points   │ │Step      │ │             │
│        │ │Step     │ │          │ │ - steps[]   │
└────────┘ └─────────┘ └──────────┘ │ - execute() │
                                     └─────────────┘
         ┌─────────────────┐
         │PipelineContext  │ (上下文)
         │  - user         │
         │  - score        │
         │  - success      │
         └─────────────────┘
```

## 核心组件

### 1. PipelineStep (接口)

定义管道中每个步骤的标准行为：

```java
public interface PipelineStep {
    void execute(PipelineContext context);
    String getStepName();
}
```

### 2. Pipeline (管道类)

负责管理和执行一系列步骤：

```java
Pipeline pipeline = new Pipeline()
    .addStep(new LoadUserStep(userId))
    .addStep(new ModifyPointsStep(points, source))
    .addStep(new SaveScoreStep());

pipeline.execute(context);
```

### 3. PipelineContext (上下文)

在步骤之间传递数据和状态：

```java
public class PipelineContext {
    private User user;
    private Score score;
    private boolean success;
    private String errorMessage;
}
```

## 设计优点

### 1. 单一职责原则
每个步骤只负责一个具体任务：
- LoadUserStep: 只负责加载用户
- ModifyPointsStep: 只负责修改积分
- SaveScoreStep: 只负责保存数据

### 2. 开闭原则
- 可以轻松添加新步骤，无需修改现有代码
- 例如：可以添加验证步骤、日志步骤、通知步骤等

### 3. 灵活组合
可以根据不同场景组合不同的步骤：

```java
// 场景1: 完整流程
Pipeline fullPipeline = new Pipeline()
    .addStep(new LoadUserStep(1L))
    .addStep(new ModifyPointsStep(50, "签到"))
    .addStep(new SaveScoreStep());

// 场景2: 只查询不保存
Pipeline queryPipeline = new Pipeline()
    .addStep(new LoadUserStep(1L))
    .addStep(new ModifyPointsStep(50, "预览"));
```

### 4. 易于测试
每个步骤可以独立测试，只需模拟上下文对象。

### 5. 错误处理
通过上下文的成功标志，可以在任意步骤中断管道执行：

```java
if (!context.isSuccess()) {
    System.out.println("管道执行失败，停止执行后续步骤");
    break;
}
```

## 运行示例

```bash
cd 11-pipeline
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.pipeline.Main"
```

## 实际应用场景

### Web请求处理
```
请求 → 认证 → 授权 → 参数验证 → 业务处理 → 日志记录 → 响应
```

### 数据处理
```
读取数据 → 清洗 → 转换 → 验证 → 计算 → 存储
```

### 订单处理
```
创建订单 → 库存检查 → 价格计算 → 支付处理 → 发货 → 通知
```

### CI/CD流程
```
代码检出 → 编译 → 单元测试 → 集成测试 → 构建镜像 → 部署
```

## 扩展思路

1. **条件步骤**: 根据上下文动态决定是否执行某个步骤
2. **并行步骤**: 支持多个步骤并行执行
3. **事务支持**: 添加回滚机制，步骤失败时撤销已执行的操作
4. **异步执行**: 支持步骤异步执行，提高性能
5. **步骤重试**: 对于失败的步骤自动重试
6. **性能监控**: 记录每个步骤的执行时间

## 与其他模式的关系

- **责任链模式**: 管道模式与责任链类似，但管道模式中每个步骤都会执行，而责任链可能只有一个处理者处理
- **装饰器模式**: 都是对对象进行逐层处理，但装饰器主要用于增强功能，管道用于流程编排
- **策略模式**: 可以在管道中使用策略模式来选择不同的步骤实现

## 总结

管道模式适用于需要按顺序处理多个步骤的场景，它提供了清晰的代码结构、良好的可维护性和高度的灵活性。通过将复杂流程分解为独立的步骤，使得系统更易于理解、测试和扩展。
