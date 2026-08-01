# MyBatis 缓存装饰器模式

## 概述
MyBatis 使用装饰器模式实现了灵活的缓存系统。通过不同装饰器的组合，可以为基础缓存添加各种增强功能。

## 类结构

```
Cache (接口 - Component)
  |
  ├── PerpetualCache (具体组件 - ConcreteComponent)
  |     基础缓存实现，使用HashMap存储
  |
  └── (装饰器们 - Decorators)
        ├── LruCache - 添加LRU淘汰策略
        ├── LoggingCache - 添加日志统计
        └── SynchronizedCache - 添加线程安全
```

## 核心组件

### 1. Cache（组件接口）
定义缓存的基本操作：
- `putObject()` - 存入缓存
- `getObject()` - 获取缓存
- `removeObject()` - 移除缓存
- `clear()` - 清空缓存
- `getSize()` - 获取大小

### 2. PerpetualCache（具体组件）
基础缓存实现：
- 使用 HashMap 存储数据
- 提供基本的增删改查功能
- 没有额外的策略或限制

### 3. 装饰器们

#### LruCache（LRU装饰器）
- **功能**：最近最少使用淘汰策略
- **实现**：使用 LinkedHashMap 跟踪访问顺序
- **作用**：防止缓存无限增长

#### LoggingCache（日志装饰器）
- **功能**：统计缓存命中率
- **实现**：记录请求次数和命中次数
- **作用**：监控缓存效果

#### SynchronizedCache（同步装饰器）
- **功能**：线程安全
- **实现**：所有方法加 synchronized
- **作用**：支持多线程环境

## MyBatis 中的实际使用

在 MyBatis 中，缓存的构建过程类似这样：

```java
// 1. 创建基础缓存
Cache cache = new PerpetualCache("MyMapper");

// 2. 根据配置添加装饰器
if (eviction == "LRU") {
    cache = new LruCache(cache);
    ((LruCache) cache).setSize(size);
}

if (readWrite) {
    cache = new SerializedCache(cache);  // 序列化
}

if (blocking) {
    cache = new BlockingCache(cache);    // 阻塞
}

// 3. 最后添加日志和同步
cache = new LoggingCache(cache);
cache = new SynchronizedCache(cache);
```

## 装饰器模式的优势

1. **灵活组合**
   - 可以根据需要选择不同的装饰器
   - 装饰器的顺序可以调整

2. **职责单一**
   - 每个装饰器只负责一个功能
   - 易于维护和扩展

3. **动态增强**
   - 运行时决定需要哪些功能
   - 不需要修改原有代码

## 装饰器顺序的重要性

```java
// 顺序1：先同步后日志
cache = new LoggingCache(new SynchronizedCache(baseCache));
// 日志记录的是同步后的操作

// 顺序2：先日志后同步
cache = new SynchronizedCache(new LoggingCache(baseCache));
// 同步包括了日志操作
```

不同的顺序可能产生不同的效果！

## 运行示例

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.designpatterns.decorator.mybatis.MyBatisCacheDemo"
```

## 扩展

如果需要添加新功能，只需创建新的装饰器：

```java
// 例：添加过期时间装饰器
public class ExpireCache implements Cache {
    private final Cache delegate;
    private Map<Object, Long> expireTime;
    
    public ExpireCache(Cache delegate) {
        this.delegate = delegate;
        this.expireTime = new HashMap<>();
    }
    
    @Override
    public Object getObject(Object key) {
        if (isExpired(key)) {
            removeObject(key);
            return null;
        }
        return delegate.getObject(key);
    }
    // ...
}
```

这就是装饰器模式的强大之处！
