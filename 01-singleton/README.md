# 单例模式 (Singleton Pattern)

## 简介
单例模式确保一个类只有一个实例，并提供一个全局访问点。

## 实现方式
本示例使用**饿汉式**实现：
- 类加载时就创建实例
- 线程安全
- 实现简单

## 项目结构
```
singleton/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── designpatterns/
                    └── singleton/
                        ├── Singleton.java    # 单例类
                        └── Main.java         # 演示类
```

## 运行方式

### 编译
```bash
cd singleton
mvn clean compile
```

### 运行
```bash
mvn exec:java -Dexec.mainClass="com.designpatterns.singleton.Main"
```

或者先打包再运行：
```bash
mvn clean package
java -cp target/singleton-1.0-SNAPSHOT.jar com.designpatterns.singleton.Main
```

## 预期输出
```
=== 单例模式示例 ===

获取第一个实例:
Singleton 实例已创建
这是单例模式的示例方法

获取第二个实例:
这是单例模式的示例方法

✓ singleton1 和 singleton2 是同一个实例
✓ 单例模式验证成功！
```

## 关键点
1. **私有构造函数**：防止外部通过 new 创建实例
2. **静态实例**：保证全局唯一
3. **公共静态方法**：提供全局访问点
