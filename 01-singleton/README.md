# 单例模式 (Singleton Pattern)

## 简介
单例模式确保一个类只有一个实例，并提供全局访问点。

## 实现方式

### 1. 饿汉式 (Singleton.java)
- 类加载时立即创建实例
- 线程安全，实现简单
- 可能造成资源浪费（即使不用也会创建）

### 2. 懒汉式 - 静态内部类 (LazySingleton.java)
- 首次调用时才创建实例
- 线程安全（JVM 保证类加载安全）
- **推荐使用**：代码简洁且性能优秀

### 3. 懒汉式 - 线程不安全 (UnsafeLazySingleton.java)
- 简单但多线程会创建多个实例
- **仅用于演示，不要在生产环境使用**

### 4. 懒汉式 - synchronized 方法 (SynchronizedLazySingleton.java)
- 线程安全但性能差
- 每次调用都要加锁，即使实例已创建

### 5. 双重检查锁 (DCLSingleton.java)
- 线程安全且高性能
- 需要 volatile 关键字防止指令重排序
- 代码复杂，容易写错

## 性能对比

测试条件：10 线程 × 100,000 次调用

| 实现方式 | 耗时 | 性能 |
|---------|------|------|
| 双重检查锁 | 9 ms | ⚡ 最快 |
| 静态内部类 | 19 ms | ✓ 推荐 |
| synchronized 方法 | 20 ms | ✗ 较慢 |

## 运行测试

```bash
cd 01-singleton
mvn clean compile exec:java -Dexec.mainClass="com.designpatterns.singleton.Main"
```

## 推荐使用
- **首选**：静态内部类（代码简洁，性能优秀）
- **备选**：双重检查锁（性能最优，但代码复杂）
- **避免**：synchronized 方法（性能差）
- **禁止**：线程不安全版本（仅用于教学演示）
