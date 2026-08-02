# 模板方法模式 vs 管道模式 - 深度对比

## 核心区别

### 模板方法模式
```
关注点：类型的行为差异
机制：继承
灵活性：流程固定
目的：算法复用 + 行为统一
```

### 管道模式
```
关注点：数据的流转加工
机制：组合
灵活性：流程可变
目的：处理步骤的动态组合
```

## 同样的饮料场景，两种实现对比

### 使用模板方法（当前实现）

**代码结构**：
```java
// 父类定义流程
public abstract class Beverage {
    public final void makeBeverage() {
        boilWater();    // 固定
        brew();         // 可变
        pourInCup();    // 固定
        addCondiments(); // 可变
    }
}

// 子类实现细节
public class Coffee extends Beverage {
    protected void brew() { /* 咖啡的冲泡 */ }
    protected void addCondiments() { /* 咖啡的调料 */ }
}
```

**使用方式**：
```java
Beverage coffee = new Coffee();
coffee.makeBeverage();  // 一次调用，自动执行所有步骤
```

**优势**：
- ✅ 流程固定，不会出错（步骤顺序在父类锁死）
- ✅ 强调不同饮料类型的行为差异
- ✅ 代码清晰，类型明确

**劣势**：
- ❌ 无法动态调整流程
- ❌ 必须使用继承

---

### 如果用管道模式

**代码结构**：
```java
// 处理器接口
interface BeverageProcessor {
    void process(BeverageContext context);
}

// 各个步骤的处理器
class BoilWaterProcessor implements BeverageProcessor { ... }
class BrewProcessor implements BeverageProcessor { ... }
class AddCondimentsProcessor implements BeverageProcessor { ... }

// 管道
class BeveragePipeline {
    private List<BeverageProcessor> processors = new ArrayList<>();
    
    public void addProcessor(BeverageProcessor processor) {
        processors.add(processor);
    }
    
    public void execute(BeverageContext context) {
        for (BeverageProcessor processor : processors) {
            processor.process(context);
        }
    }
}
```

**使用方式**：
```java
// 需要手动构建管道
BeveragePipeline pipeline = new BeveragePipeline();
pipeline.addProcessor(new BoilWaterProcessor());
pipeline.addProcessor(new BrewCoffeeProcessor());
pipeline.addProcessor(new PourInCupProcessor());
pipeline.addProcessor(new AddSugarProcessor());
pipeline.addProcessor(new AddMilkProcessor());

BeverageContext context = new BeverageContext();
pipeline.execute(context);
```

**优势**：
- ✅ 可以动态增删处理器
- ✅ 可以动态调整顺序
- ✅ 处理器独立，可复用

**劣势**：
- ❌ 流程不受控，可能构建错误的顺序
- ❌ 代码更复杂，需要维护处理器列表
- ❌ 类型不明确，都是 Pipeline

---

## 为什么饮料场景更适合模板方法？

### 1. 流程必须固定
制作饮料的步骤顺序是严格的：
```
烧水 → 冲泡 → 倒杯 → 加料
```

如果用管道模式，开发者可能会：
```java
pipeline.addProcessor(new PourInCupProcessor());  // 先倒杯
pipeline.addProcessor(new BoilWaterProcessor());   // 后烧水 ❌
```
这样的代码会通过编译，但运行时逻辑错误！

### 2. 强调类型差异
Coffee、Tea、HotChocolate 是**不同类型**的饮料，它们的行为有本质差异。

模板方法清晰表达了这种类型关系：
```java
Beverage coffee = new Coffee();  // 类型明确
Beverage tea = new Tea();        // 一看就知道是什么
```

管道模式则是：
```java
BeveragePipeline pipeline1 = new BeveragePipeline(); // 这是什么饮料？
BeveragePipeline pipeline2 = new BeveragePipeline(); // 看不出来
```

### 3. 业务语义清晰
```java
coffee.makeBeverage();  // 语义：制作这杯咖啡
```

vs

```java
pipeline.execute(context);  // 语义：执行管道（太技术化）
```

---

## 什么时候必须用管道模式？

### 场景 1：数据处理流水线
```java
// 图片处理
ImagePipeline pipeline = new ImagePipeline();
pipeline.add(new ResizeProcessor(800, 600));
pipeline.add(new WatermarkProcessor("logo.png"));
pipeline.add(new CompressProcessor(80));
pipeline.add(new FormatConverter("webp"));

// 不同用户可能需要不同的处理流程
if (isPremiumUser) {
    pipeline.add(new HDEnhanceProcessor());
}

Image result = pipeline.process(originalImage);
```

**为什么必须用管道**：
- 流程需要根据条件动态调整
- 处理器可以复用到其他场景
- 强调的是数据的变换过程

### 场景 2：HTTP 请求处理
```java
// Servlet Filter Chain 就是管道模式
FilterChain chain = new FilterChain();
chain.add(new AuthenticationFilter());
chain.add(new LoggingFilter());
chain.add(new RateLimitFilter());
chain.add(new BusinessFilter());

chain.doFilter(request, response);
```

**为什么必须用管道**：
- 过滤器可以动态配置
- 不同接口可能需要不同的过滤器组合
- 过滤器需要独立测试和维护

---

## 决策树：选择哪种模式？

```
┌─ 需求：处理一个流程
│
├─ 问题 1：流程步骤的顺序能改吗？
│  ├─ 不能改 → 倾向模板方法
│  └─ 需要动态调整 → 倾向管道模式
│
├─ 问题 2：这是在描述"类型的行为"还是"数据的加工"？
│  ├─ 类型的行为（Coffee, Tea 是不同类型）→ 模板方法
│  └─ 数据的加工（Image 经过多次处理）→ 管道模式
│
├─ 问题 3：步骤需要独立复用到其他场景吗？
│  ├─ 不需要，只在这个场景用 → 模板方法
│  └─ 需要复用（如 Filter 用于多个接口）→ 管道模式
│
└─ 问题 4：错误的步骤顺序是否应该被阻止？
   ├─ 必须阻止（如数据库事务处理）→ 模板方法
   └─ 允许灵活组合 → 管道模式
```

---

## 实战建议

### 优先使用模板方法的场景
- 框架设计：定义扩展点
- 流程类操作：文件处理、数据库操作
- 生命周期管理：初始化、销毁
- 测试框架：setUp、test、tearDown

### 优先使用管道模式的场景
- 数据处理流水线
- HTTP 中间件
- 消息处理链
- 插件系统

### 两者结合
有时可以结合使用：
```java
// 模板方法定义大框架
public abstract class DataProcessor {
    public final void process(Data data) {
        validate(data);
        Pipeline pipeline = buildPipeline();  // 子类构建管道
        pipeline.execute(data);
        saveResult(data);
    }
    
    protected abstract Pipeline buildPipeline();
}
```

---

## 总结

模板方法模式不是因为"功能不够"才不用管道模式，而是因为：

1. **业务语义更清晰** - Coffee 就是 Coffee，不是 Pipeline
2. **类型安全更高** - 编译期就能发现问题
3. **代码更简单** - 不需要手动组装管道
4. **防止误用** - 流程固定，不会被错误修改

选择设计模式的关键不是"哪个更灵活"，而是**哪个更匹配业务语义和约束需求**。
