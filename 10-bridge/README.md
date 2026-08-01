# 桥接模式（Bridge Pattern）

## 模式简介

桥接模式是一种结构型设计模式，它将抽象部分与实现部分分离，使它们都可以独立变化。

**核心思想**：用组合代替继承，避免类爆炸。

## 问题场景

云原生日志收集系统需要支持：
- **多种日志格式**：JSON、XML、PlainText、Protobuf（4种）
- **多种存储后端**：File、Console、Elasticsearch、Kafka、S3（5种）

### 传统继承方案的问题

如果用继承实现所有组合：

```
4种格式 × 5种存储 = 20个子类 + 1个基类 = 21个类
```

这会导致**类爆炸**问题，而且扩展性很差：
- 增加1种新格式 → 需要新增5个类
- 增加1种新存储 → 需要新增4个类

## 桥接模式解决方案

### 核心思想

将两个独立变化的维度分离：
- **抽象部分（格式化）**：通过继承扩展
- **实现部分（存储）**：通过接口扩展
- **桥梁**：抽象部分持有实现部分的接口引用

### 类结构

```
抽象部分（维度1：格式化）          实现部分（维度2：存储）
LogController (抽象类)            Storage (接口)
      |                              |
      | 持有（桥梁）─────────────────>|
      |                              |
      | 继承                          | 实现
      |                              |
      ├─ JSONLogController          ├─ FileStorage
      ├─ XMLLogController           ├─ ConsoleStorage
      ├─ PlainTextLogController     ├─ ElasticsearchStorage
      └─ ProtobufLogController      ├─ KafkaStorage
                                    └─ S3Storage
```

### 项目结构

```
src/main/java/com/designpatterns/bridge/
├── Main.java                           # 演示入口
├── abstraction/                        # 抽象部分（格式化维度）
│   ├── LogController.java             # 抽象类（持有Storage引用）
│   ├── JSONLogController.java         # JSON格式实现
│   ├── XMLLogController.java          # XML格式实现
│   ├── PlainTextLogController.java    # 纯文本格式实现
│   └── ProtobufLogController.java     # Protobuf格式实现
└── implementation/                     # 实现部分（存储维度）
    ├── Storage.java                   # 存储接口
    ├── FileStorage.java              # 文件存储
    ├── ConsoleStorage.java           # 控制台存储
    ├── ElasticsearchStorage.java     # ES存储（模拟）
    ├── KafkaStorage.java             # Kafka存储（模拟）
    └── S3Storage.java                # S3存储（模拟）
```

## 代码示例

### 使用方式

```java
// 创建存储实例
Storage fileStorage = new FileStorage("logs/app.log");
Storage esStorage = new ElasticsearchStorage("localhost:9200", "logs");

// JSON格式 + 文件存储
LogController controller1 = new JSONLogController(fileStorage);
controller1.process("用户登录成功");

// XML格式 + Elasticsearch存储
LogController controller2 = new XMLLogController(esStorage);
controller2.process("订单创建完成");

// 可以自由组合：4种格式 × 5种存储 = 20种组合
```

### 核心代码

```java
// 抽象类：持有Storage接口（桥梁）
abstract class LogController {
    protected Storage storage;  // 桥梁
    
    public LogController(Storage storage) {
        this.storage = storage;
    }
    
    public abstract void process(String log);
}

// 具体实现：JSON格式
class JSONLogController extends LogController {
    public void process(String log) {
        String json = formatToJSON(log);  // 格式化
        storage.save(json);                // 通过桥梁存储
    }
}
```

## 优势对比

### 类数量对比

| 方案 | 类数量 | 说明 |
|------|-------|------|
| **继承方案** | 21个类 | 4×5个子类 + 1个基类 |
| **桥接模式** | 11个类 | 4个格式类 + 5个存储类 + 2个基础类 |
| **减少** | **10个类** | **减少47.6%** |

### 扩展性对比

| 场景 | 继承方案 | 桥接模式 |
|------|---------|---------|
| 增加1种新格式 | 新增5个类 | 新增1个类 ✅ |
| 增加1种新存储 | 新增4个类 | 新增1个类 ✅ |
| 同时增加1格式+1存储 | 新增9个类 | 新增2个类 ✅ |

## 运行示例

```bash
# 编译并运行
mvn clean compile exec:java -Dexec.mainClass="com.designpatterns.bridge.Main"
```

### 运行输出

```
=== 桥接模式演示：日志收集系统 ===
演示4种格式 × 5种存储 = 20种组合

【演示1】JSON格式 + 不同存储
- JSON + 文件存储:
日志已写入文件: logs/application.log
- JSON + Elasticsearch存储:
[Elasticsearch] 发送到 localhost:9200/logs: {"message":"订单创建",...}
...

【优势总结】
- 可组合数量：4×5 = 20种组合
- 实际类数量：11个类
- 对比继承方案：减少47.6%的类
```

## 关键设计

### 1. 桥梁的位置

```java
abstract class LogController {
    protected Storage storage;  // 🌉 这是桥梁！
}
```

### 2. 两个维度的分离

- **格式化维度**：通过继承LogController实现（JSONLogController、XMLLogController等）
- **存储维度**：通过实现Storage接口（FileStorage、KafkaStorage等）

### 3. 独立变化

两个维度可以独立扩展，互不影响：
- 增加新格式：继承LogController
- 增加新存储：实现Storage接口

## 适用场景

### 何时使用桥接模式？

1. **两个独立变化的维度**：如本例的格式化和存储
2. **避免类爆炸**：继承方案会导致N×M个类
3. **运行时切换实现**：可以动态改变存储方式

### 实际应用场景

- **跨平台UI框架**：抽象UI + 平台实现（Windows/Mac/Linux）
- **数据库驱动**：抽象JDBC + 具体数据库实现（MySQL/Oracle/PostgreSQL）
- **消息系统**：抽象消息 + 传输协议（HTTP/TCP/WebSocket）
- **图形渲染**：抽象图形 + 渲染引擎（OpenGL/DirectX/Vulkan）

## 与其他模式的区别

### 桥接模式 vs 策略模式

| 维度 | 桥接模式 | 策略模式 |
|------|---------|---------|
| 目的 | 分离抽象和实现 | 封装算法 |
| 抽象类 | 有抽象类和子类 | 通常是具体类 |
| 维度数量 | 两个维度 | 一个维度 |

### 桥接模式 vs 装饰器模式

| 维度 | 桥接模式 | 装饰器模式 |
|------|---------|-----------|
| 目的 | 分离抽象和实现 | 动态增加职责 |
| 接口 | 不同的接口 | 同一接口 |
| 嵌套 | 不嵌套 | 可以多层嵌套 |

## 学习要点

1. ✅ 理解"组合优于继承"的原则
2. ✅ 识别系统中独立变化的维度
3. ✅ 掌握"桥梁"的设计（抽象持有实现的引用）
4. ✅ 理解两个维度如何独立变化

## 参考资料

- 《设计模式：可复用面向对象软件的基础》- GoF
- [桥接模式详解](../docs/bridge-mode.md)

---

## 当前存在的问题

### 1. Primitive Obsession（基本类型偏执）

**问题描述**：
- 目前用 `String` 直接表示日志内容
- 日志应该包含更多信息：级别（INFO/WARN/ERROR）、来源、时间戳等
- 缺少类型安全性和语义清晰度

**改进方向**：
```java
// 创建LogMessage类封装日志信息
public class LogMessage {
    private String message;
    private LogLevel level;      // INFO/WARN/ERROR
    private String source;        // 日志来源
    private long timestamp;
    private Map<String, String> metadata;  // 扩展字段
}

// LogController.process()改为接收LogMessage
public abstract void process(LogMessage logMessage);
```

**优点**：
- 更符合面向对象设计
- 便于扩展日志字段
- 类型安全

**缺点**：
- 增加复杂度（对于demo项目来说）
- 需要修改所有LogController和测试代码

---

### 2. Error Handling（错误处理不完善）

**问题描述**：
- `FileStorage`捕获了IOException但只打印错误，调用者不知道失败
- 其他Storage（ES、Kafka、S3）是mock的，没有真实错误处理
- 没有重试机制

**改进方向**：
```java
// 方案A：修改Storage接口，返回结果
public interface Storage {
    boolean save(String data);  // 返回是否成功
}

// 方案B：抛出自定义异常
public interface Storage {
    void save(String data) throws StorageException;
}

// 方案C：返回Result对象
public interface Storage {
    Result<Void> save(String data);  // Result包含成功/失败信息
}
```

**优点**：
- 调用者能感知错误
- 可以实现重试逻辑
- 更符合生产环境要求

**缺点**：
- 增加复杂度
- 需要决定错误处理策略（返回值 vs 异常）

---

### 3. 缺少日志级别过滤

**问题描述**：
- 所有日志都会被保存，无法根据级别过滤
- 无法实现"只记录ERROR级别日志到文件"这类需求

**改进方向**：
```java
// 增加LogLevel枚举
public enum LogLevel {
    DEBUG, INFO, WARN, ERROR
}

// LogController增加级别过滤
public abstract class LogController {
    protected Storage storage;
    protected LogLevel minLevel = LogLevel.INFO;  // 最低记录级别
    
    public void process(LogMessage msg) {
        if (msg.getLevel().ordinal() >= minLevel.ordinal()) {
            doProcess(msg);
        }
    }
    
    protected abstract void doProcess(LogMessage msg);
}
```

---

### 4. 性能问题

**问题描述**：
- 同步写入，可能阻塞主线程
- 没有批量写入支持
- 没有缓冲机制

**改进方向**：
```java
// 异步写入
public class AsyncStorage implements Storage {
    private ExecutorService executor = Executors.newFixedThreadPool(5);
    private Storage delegate;
    
    public void save(String data) {
        executor.submit(() -> delegate.save(data));
    }
}

// 批量写入
public class BatchStorage implements Storage {
    private List<String> buffer = new ArrayList<>();
    private int batchSize = 100;
    
    public void save(String data) {
        buffer.add(data);
        if (buffer.size() >= batchSize) {
            flush();
        }
    }
}
```

---

## 未来可以做什么

### 1. 增加第三个维度：数据处理

**场景**：在存储前对日志进行处理
- 压缩（Gzip）
- 加密（AES）
- 脱敏（隐藏敏感信息）
- 采样（只保存10%的日志）

**实现方式**：
```java
// 增加Processor接口
public interface Processor {
    String process(String data);
}

// LogController持有Processor链
public abstract class LogController {
    protected Storage storage;
    protected List<Processor> processors = new ArrayList<>();
    
    public void process(String log) {
        String formatted = format(log);
        // 依次处理
        for (Processor p : processors) {
            formatted = p.process(formatted);
        }
        storage.save(formatted);
    }
}
```

**优点**：
- 增加了处理能力
- 符合责任链模式
- 可组合多个处理器

---

### 2. 实现真实的存储后端

**当前状态**：ES、Kafka、S3都是mock的

**改进方向**：
```java
// 使用真实的客户端
public class ElasticsearchStorage implements Storage {
    private RestHighLevelClient client;
    
    public void save(String data) {
        IndexRequest request = new IndexRequest("logs")
            .source(data, XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }
}

// Kafka使用KafkaProducer
public class KafkaStorage implements Storage {
    private KafkaProducer<String, String> producer;
    
    public void save(String data) {
        producer.send(new ProducerRecord<>("log-topic", data));
    }
}
```

**优点**：
- 可以用于生产环境
- 测试真实的错误场景

**需要的工作**：
- 添加依赖（elasticsearch、kafka-clients、aws-sdk）
- 配置连接参数
- 处理真实的异常

---

### 3. 增加配置化支持

**场景**：通过配置文件动态创建LogController

**实现方式**：
```yaml
# logging-config.yaml
loggers:
  - name: app-logger
    format: JSON
    storage:
      type: file
      path: logs/app.log
      
  - name: error-logger
    format: XML
    storage:
      type: elasticsearch
      host: localhost:9200
      index: errors
```

```java
// 配置加载器
public class LoggerFactory {
    public static LogController create(LoggerConfig config) {
        Storage storage = createStorage(config.getStorage());
        
        switch (config.getFormat()) {
            case "JSON": return new JSONLogController(storage);
            case "XML": return new XMLLogController(storage);
            // ...
        }
    }
}
```

---

### 4. 增加监控和指标

**场景**：统计日志系统的运行状况

**实现方式**：
```java
// 装饰器模式增加监控
public class MetricsStorage implements Storage {
    private Storage delegate;
    private Counter saveCounter = new Counter();
    private Timer saveTimer = new Timer();
    
    public void save(String data) {
        Timer.Context ctx = saveTimer.time();
        try {
            delegate.save(data);
            saveCounter.inc();
        } finally {
            ctx.stop();
        }
    }
    
    public Metrics getMetrics() {
        return new Metrics(saveCounter.get(), saveTimer.getAvg());
    }
}
```

**监控指标**：
- 日志写入速率（TPS）
- 写入延迟（P50/P99）
- 失败率
- 各格式/存储的使用统计

---

### 5. 支持插件化扩展

**场景**：通过SPI机制动态加载Format和Storage

**实现方式**：
```java
// 定义SPI接口
public interface FormatProvider {
    String getName();
    LogController create(Storage storage);
}

public interface StorageProvider {
    String getName();
    Storage create(Map<String, String> config);
}

// META-INF/services/com.designpatterns.bridge.FormatProvider
com.example.YAMLLogControllerProvider
com.example.JSONLinesLogControllerProvider

// 动态加载
ServiceLoader<FormatProvider> formats = 
    ServiceLoader.load(FormatProvider.class);
```

**优点**：
- 不修改核心代码就能扩展
- 支持第三方插件
- 更符合开闭原则

---

### 6. 增加日志路由功能

**场景**：根据日志内容路由到不同存储

**实现方式**：
```java
public class RoutingLogController extends LogController {
    private Map<Predicate<String>, Storage> routes;
    
    public void process(String log) {
        String formatted = format(log);
        
        for (Map.Entry<Predicate<String>, Storage> route : routes.entrySet()) {
            if (route.getKey().test(log)) {
                route.getValue().save(formatted);
            }
        }
    }
}

// 使用示例
RoutingLogController controller = new RoutingLogController();
controller.addRoute(
    log -> log.contains("ERROR"),
    new FileStorage("errors.log")
);
controller.addRoute(
    log -> log.contains("payment"),
    new ElasticsearchStorage("localhost", "payments")
);
```

---

### 7. 增加单元测试

**当前状态**：只有Main类的演示，没有单元测试

**改进方向**：
```java
@Test
public void testJSONLogController() {
    // 使用Mock Storage
    Storage mockStorage = mock(Storage.class);
    LogController controller = new JSONLogController(mockStorage);
    
    controller.process("test message");
    
    // 验证格式化结果
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(mockStorage).save(captor.capture());
    
    String saved = captor.getValue();
    assertTrue(saved.contains("\"message\":\"test message\""));
}
```

---

## 总结

### 当前项目的定位
这是一个**教学演示项目**，重点在于：
- ✅ 清晰展示桥接模式的核心思想
- ✅ 代码简洁易懂
- ✅ 完整的20种组合测试

### 生产环境所需的改进
如果要用于生产，需要考虑：
1. 完善的错误处理和重试机制
2. 异步写入和批量处理（性能）
3. 监控指标和告警
4. 单元测试和集成测试
5. 配置化和插件化支持
6. 日志级别和过滤
7. 真实的存储客户端实现

### 学习建议
建议按以下顺序学习和改进：
1. 先理解当前的桥接模式实现 ✅（已完成）
2. 尝试添加一个新格式（YAML）和新存储（MongoDB）
3. 实现日志级别过滤功能
4. 增加单元测试
5. 实现真实的Kafka或ES存储
6. 添加异步和批量处理
7. 最后考虑插件化和配置化
