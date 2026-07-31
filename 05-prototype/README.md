# 原型模式 (Prototype Pattern)

## 定义
原型模式是一种创建型设计模式，通过复制（克隆）现有对象来创建新对象，而不是通过new关键字创建。

## 核心概念

### 1. 浅拷贝 vs 深拷贝
- **浅拷贝**：只复制对象本身和基本类型字段，引用类型字段只复制引用地址
- **深拷贝**：递归复制所有引用类型字段，创建完全独立的对象

### 2. 实现深拷贝的方式
- **手动拷贝**：在 clone() 方法中手动创建引用类型字段的新实例
- **序列化方式**：通过序列化和反序列化实现深拷贝（适合复杂对象图）

### 3. 原型注册表（Prototype Registry）
- 管理一组预定义的原型对象
- 客户端通过名称/ID获取克隆对象
- 类似于对象池模式，但返回的是克隆对象

### 4. 性能优势
- 当对象创建成本高时，克隆比构造快得多
- 避免重复执行耗时的初始化操作（数据库查询、网络请求、复杂计算等）

## 使用场景
- 创建对象成本较高时（数据库读取、复杂初始化）
- 需要创建大量相似对象时
- 需要保护原始对象，避免被修改时
- 对象的创建过程独立于具体类型时

## 优缺点

### 优点
- ✅ 性能优异：克隆比重新创建快
- ✅ 简化对象创建：不需要知道具体类
- ✅ 可以动态增减产品：通过注册表管理
- ✅ 减少子类构造：不需要为每种配置创建子类

### 缺点
- ❌ 深拷贝实现复杂：需要递归处理所有引用
- ❌ 循环引用问题：对象图存在循环引用时需要特殊处理
- ❌ 必须实现Cloneable：需要修改现有类

## 示例说明

### 基础示例
- `Main.java` - 基本的文档克隆演示
- `Document.java` - 实现Cloneable的文档类

### 浅拷贝 vs 深拷贝
- `ShallowCopyDemo.java` - 演示浅拷贝的问题（引用类型字段共享）
- `DeepCopyDemo.java` - 演示深拷贝的解决方案（手动拷贝引用字段）

### 序列化深拷贝
- `SerializationCloneDemo.java` - 通过序列化实现深拷贝
- 适合复杂对象图，自动处理所有引用类型

### 原型注册表
- `PrototypeRegistryDemo.java` - 演示原型注册表模式
- `ShapeCache` - 缓存并管理预定义的原型对象

### 性能对比
- `PerformanceComparisonDemo.java` - 对比构造函数 vs 克隆的性能差异
- 克隆性能提升 **99.92%**！

## 运行示例

```bash
# 编译
mvn clean compile

# 基础示例
mvn exec:java -Dexec.mainClass="com.designpatterns.prototype.Main"

# 浅拷贝问题演示
mvn exec:java -Dexec.mainClass="com.designpatterns.prototype.ShallowCopyDemo"

# 深拷贝解决方案
mvn exec:java -Dexec.mainClass="com.designpatterns.prototype.DeepCopyDemo"

# 序列化深拷贝
mvn exec:java -Dexec.mainClass="com.designpatterns.prototype.SerializationCloneDemo"

# 原型注册表
mvn exec:java -Dexec.mainClass="com.designpatterns.prototype.PrototypeRegistryDemo"

# 性能对比
mvn exec:java -Dexec.mainClass="com.designpatterns.prototype.PerformanceComparisonDemo"
```

## 关键点总结

1. **String是不可变的**：所以浅拷贝看起来像深拷贝
2. **引用类型需要注意**：List、Map、自定义对象需要深拷贝
3. **序列化很方便**：但性能相对较低，需要实现Serializable
4. **注册表很实用**：适合管理预定义的原型对象
5. **性能是核心优势**：创建成本高时，克隆比构造快得多
