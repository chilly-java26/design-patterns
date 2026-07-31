# 建造者模式 (Builder Pattern)

## 简介
建造者模式将复杂对象的构建过程与表示分离，使得同样的构建过程可以创建不同的表示。

## 核心思想
- **分步构建**：通过一步步设置属性来构建对象
- **链式调用**：返回 this，支持流畅的 API
- **不可变对象**：一旦创建，属性不可修改
- **必填可选**：明确区分必填和可选参数

## 适用场景

建造者模式特别适合：
- ✅ 参数多（5个以上）
- ✅ 有必填和可选参数
- ✅ 需要不可变对象
- ✅ 构建过程复杂
- ✅ 需要参数验证

## 类结构

### 1. 产品类：Computer
**属性**（final，不可变）：
- 必填：CPU（处理器）
- 必填：RAM（内存）
- 必填：Storage（硬盘）
- 可选：GPU（显卡）
- 可选：PowerSupply（电源）
- 可选：Case（机箱）

### 2. 建造者：Computer.Builder
**职责**：
- 提供链式调用方法设置每个属性
- 验证必填项
- 构建最终的 Computer 对象

### 3. 指挥者：ComputerDirector
**职责**：
- 封装常见配置方案
- 如：游戏电脑、办公电脑、服务器

## 使用方式

### 方式 1：手动构建（最灵活）

```java
Computer pc = new Computer.Builder()
    .cpu("Intel Core i9-13900K")
    .ram("32GB DDR5")
    .storage("2TB NVMe SSD")
    .gpu("NVIDIA RTX 4090")  // 可选
    .powerSupply("850W 80+ Gold")  // 可选
    .pcCase("NZXT H510 Elite")  // 可选
    .build();
```

**优点**：完全自定义，想要什么加什么

---

### 方式 2：只设置必填项

```java
Computer minimalPC = new Computer.Builder()
    .cpu("Intel Core i7")
    .ram("16GB")
    .storage("512GB SSD")
    .build();  // 可选项使用默认值
```

**优点**：简洁，只设置必要的配置

---

### 方式 3：使用 Director（最简单）

```java
Computer gamingPC = ComputerDirector.buildGamingComputer();
Computer officePC = ComputerDirector.buildOfficeComputer();
Computer serverPC = ComputerDirector.buildServerComputer();
```

**优点**：一行代码搞定，预设配置开箱即用

---

## 与传统方式对比

### ❌ 传统构造函数（不推荐）
```java
// 参数太多，容易搞混顺序
Computer pc = new Computer("i9", "32GB", "1TB", "RTX 4090", "850W", "NZXT");
// 第4个参数是显卡还是电源？容易出错...
```

### ✅ 建造者模式（推荐）
```java
// 清晰、链式调用、参数顺序无关
Computer pc = new Computer.Builder()
    .cpu("Intel i9")
    .ram("32GB DDR5")
    .storage("1TB SSD")
    .gpu("RTX 4090")
    .build();
```

---

## 核心优势

### 1. 解决"参数爆炸"问题
不用记忆参数顺序，每个参数都有明确的方法名。

### 2. 必填和可选参数控制
```java
// 必填项：不设置就报错
builder.cpu("i9");   // 必须
builder.ram("32GB"); // 必须
builder.storage("1TB"); // 必须

// 可选项：不设置使用默认值
builder.gpu("RTX 4090");  // 可选
```

### 3. 不可变对象（Immutable）
```java
Computer pc = builder.build();
// pc 的属性是 final 的，创建后不能修改
// pc.setCpu("i7");  // ✗ 编译错误，没有 setter
```

**好处**：
- 线程安全
- 防止被意外修改
- 符合函数式编程思想

### 4. 链式调用
```java
return this;  // 返回自己，支持链式调用

new Computer.Builder()
    .cpu("i9")
    .ram("32GB")
    .storage("1TB")
    .build();
```

---

## 运行示例

```bash
cd 04-builder
mvn clean compile exec:java -Dexec.mainClass="com.designpatterns.builder.BuilderDemo"
```

## 输出示例

```
=== 建造者模式演示 ===

【方式 1：手动构建 - 完全自定义配置】
========== 电脑配置 ==========
CPU:    AMD Ryzen 9 7950X
内存:   64GB DDR5
硬盘:   1TB NVMe SSD
显卡:   AMD Radeon RX 7900 XTX
电源:   750W 80+ Platinum
机箱:   Fractal Design Meshify C
==============================

【方式 2：使用 Director - 游戏电脑】
========== 电脑配置 ==========
CPU:    Intel Core i9-13900K
内存:   32GB DDR5
硬盘:   2TB NVMe SSD
显卡:   NVIDIA GeForce RTX 4090
电源:   850W 80+ Gold
机箱:   NZXT H510 Elite
==============================

【演示：必填项验证】
✗ 构建失败：内存是必填项，不能为空
✗ 构建失败：硬盘是必填项，不能为空
```

---

## 与工厂模式的对比

| 特性 | 工厂模式 | 建造者模式 |
|------|---------|-----------|
| 目的 | 创建**不同类型**的对象 | 创建**同一类型但配置复杂**的对象 |
| 参数 | 少（通常1-2个） | 多（5个以上） |
| 构建过程 | 一步到位 | 分步骤构建 |
| 示例 | 创建 Circle、Square | 创建不同配置的 Computer |
| 使用场景 | 产品种类多 | 产品配置多 |

---

## Director 的作用

**Director = 预设配置方案**

```
ComputerDirector
├── buildGamingComputer()      → 游戏电脑配置
├── buildOfficeComputer()      → 办公电脑配置
├── buildServerComputer()      → 服务器配置
├── buildDeveloperWorkstation() → 开发者工作站
└── buildBudgetComputer()      → 入门级电脑
```

**好处**：
- 用户不用关心细节
- 常见配置一键生成
- 配置集中管理，易于维护

---

## 实际应用

建造者模式在实际开发中非常常见：

### Java 标准库
- `StringBuilder`
- `Stream.Builder`
- `Calendar.Builder`

### 流行框架
- **Retrofit**: `Retrofit.Builder()`
- **OkHttp**: `OkHttpClient.Builder()`
- **Lombok**: `@Builder` 注解

### 示例（OkHttp）
```java
OkHttpClient client = new OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor)
    .build();
```

---

## 优点总结

✅ **参数清晰**：`builder.cpu("i9")` 比构造函数参数更易读  
✅ **链式调用**：流畅的 API，代码优雅  
✅ **必填可选**：明确哪些是必填，哪些是可选  
✅ **不可变对象**：线程安全，防止被意外修改  
✅ **灵活扩展**：新增属性不影响现有代码  
✅ **Director 封装**：常见配置一键生成  

---

## 何时使用建造者模式

### ✅ 适合使用
- 参数多（5个以上）
- 有必填和可选参数
- 需要不可变对象
- 构建过程复杂
- 参数之间有依赖关系

### ✗ 不适合使用
- 参数少（3个以下）
- 对象简单，无复杂构建逻辑
- 不需要不可变对象

---

## 总结

**建造者模式**：像搭积木一样，一步步组装出复杂对象。

**核心价值**：
- 解决"参数爆炸"问题
- 创建不可变对象
- 提供流畅的 API
- 封装复杂的构建逻辑

**一句话**：当构造函数参数超过4个，就该考虑建造者模式了！
