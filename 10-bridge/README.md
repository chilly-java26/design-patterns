# 桥接模式（Bridge Pattern）

## 问题场景

云原生日志收集系统需要支持：
- **多种日志格式**：JSON、XML、PlainText、Protobuf
- **多种存储后端**：Elasticsearch、Kafka、S3、LocalFile

如果用继承实现所有组合，会导致类爆炸（4×4=16个类）。

## 桥接模式解决方案

将抽象部分（格式化）与实现部分（存储）分离，使它们可以独立变化。

### 结构

```
维度1（抽象 - 通过继承）        维度2（实现 - 通过接口）
LogController (抽象类)          Storage (接口)
      |                              |
      | 持有（桥梁）─────────────────>|
      |                              |
      | 继承                          | 实现
      |                              |
      ├─ JSONLogController          ├─ ElasticsearchStorage
      ├─ XMLLogController           ├─ KafkaStorage
      ├─ PlainTextLogController     ├─ S3Storage
      └─ ProtobufLogController      └─ LocalFileStorage
```

### 优势

- 类的数量从 N×M 降低到 N+M（从16个降到8个）
- 两个维度可以独立变化
- 增加新格式只需要新增子类
- 增加新存储只需要新增实现类

## 运行示例

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.bridge.Main"
```
