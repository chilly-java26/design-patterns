# 观察者模式（Observer Pattern）

## 概述
观察者模式定义了对象之间的一对多依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都会得到通知并自动更新。

## 核心角色

### 1. Observer（观察者接口）
- `Observer` - 定义更新接口

### 2. Subject（被观察者接口）
- `Subject` - 定义添加、删除、通知观察者的接口

### 3. ConcreteSubject（具体被观察者）
- `WeChatAccount` - 微信公众号，维护粉丝列表

### 4. ConcreteObserver（具体观察者）
- `User` - 用户/粉丝，接收推送消息

## 类比理解
就像订阅公众号：
- **关注** = attach（注册观察者）
- **取关** = detach（移除观察者）
- **发文章** = notifyObservers（通知所有观察者）
- **收到推送** = update（观察者收到通知）

## 使用场景
- 事件监听系统
- 消息订阅系统
- MVC架构中的模型-视图关系
- 发布-订阅模式
- GUI事件处理

## 优点
- 观察者和被观察者松耦合
- 支持广播通信
- 符合开闭原则，容易扩展

## 运行示例

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.designpatterns.observer.ObserverDemo"
```

## 实际应用
- Java的Observer/Observable（已过时，但思想保留）
- Spring的ApplicationEvent/ApplicationListener
- GUI的事件监听器
- 消息队列的发布订阅
