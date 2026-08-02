# 命令模式 (Command Pattern)

## 定义

**命令模式是一种行为型设计模式，它将请求封装成对象，从而使你可以用不同的请求对客户进行参数化，对请求排队或记录请求日志，以及支持可撤销的操作。**

## 核心思想

**将"做什么"（命令）和"谁来做"（接收者）分离**，请求者只需要调用命令对象的执行方法，不需要知道具体是谁在执行、怎么执行的。

## 结构

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │ 创建
       ▼
┌─────────────────┐      ┌──────────────┐
│ ConcreteCommand │─────>│   Receiver   │
└────────┬────────┘ 持有  └──────────────┘
         │                │ 真正执行操作
         │ 实现
         ▼
    ┌─────────┐
    │ Command │
    └────▲────┘
         │ 调用
    ┌────┴────┐
    │ Invoker │
    └─────────┘
```

## 角色说明

1. **Command（命令接口）**: 定义命令的执行接口
2. **ConcreteCommand（具体命令）**: 实现命令接口，绑定接收者
3. **Receiver（接收者）**: 真正执行操作的对象
4. **Invoker（调用者）**: 持有命令对象，负责调用命令
5. **Client（客户端）**: 创建具体命令并设置接收者

## 项目结构

```
20-command/
├── pom.xml
└── src/main/java/com/designpatterns/command/
    ├── Command.java              # 命令接口
    ├── NoCommand.java            # 空命令（空对象模式）
    ├── Light.java                # 接收者：电灯
    ├── Fan.java                  # 接收者：风扇
    ├── LightOnCommand.java       # 具体命令：打开灯
    ├── LightOffCommand.java      # 具体命令：关闭灯
    ├── FanHighCommand.java       # 具体命令：风扇高速
    ├── FanOffCommand.java        # 具体命令：关闭风扇
    ├── MacroCommand.java         # 宏命令（组合多个命令）
    ├── RemoteControl.java        # 调用者：遥控器
    └── CommandDemo.java          # 演示类
```

## 核心代码

### 1. 命令接口

```java
public interface Command {
    void execute();  // 执行命令
    void undo();     // 撤销命令
}
```

### 2. 接收者

```java
public class Light {
    public void on() { System.out.println("灯打开了"); }
    public void off() { System.out.println("灯关闭了"); }
}
```

### 3. 具体命令

```java
public class LightOnCommand implements Command {
    private Light light;  // 持有接收者
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    public void execute() {
        light.on();  // 调用接收者的方法
    }
    
    public void undo() {
        light.off();
    }
}
```

### 4. 调用者

```java
public class RemoteControl {
    private Command command;
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public void pressButton() {
        command.execute();  // 不知道具体是谁在执行
    }
}
```

## 运行示例

```bash
cd 20-command
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.command.CommandDemo"
```

## 输出示例

```
========== 命令模式演示 ==========

------ 遥控器 ------
[插槽 0] LightOnCommand    LightOffCommand
[插槽 1] LightOnCommand    LightOffCommand
[插槽 2] FanHighCommand    FanOffCommand
[插槽 3] NoCommand    NoCommand
...

--- 测试基本功能 ---
客厅的灯打开了
客厅的灯关闭了
卧室的灯打开了
客厅的风扇设置为高速

--- 测试撤销功能 ---
客厅的风扇关闭了
卧室的灯关闭了
卧室的灯打开了

--- 测试宏命令（派对模式）---
执行派对模式：
客厅的灯打开了
卧室的灯打开了
客厅的风扇设置为高速

关闭派对模式：
客厅的灯关闭了
卧室的灯关闭了
客厅的风扇关闭了

撤销关闭派对模式：
客厅的风扇设置为高速
卧室的灯打开了
客厅的灯打开了
```

## 应用场景

1. **需要解耦请求发送者和接收者**
   - GUI 按钮和菜单项
   - 工具栏操作

2. **需要支持撤销/恢复操作**
   - 文本编辑器的撤销功能
   - 图形编辑器的操作历史

3. **需要支持事务和日志记录**
   - 数据库事务
   - 操作日志记录

4. **需要参数化对象执行不同操作**
   - 遥控器不同按钮执行不同命令
   - 调度任务

5. **需要支持宏命令（批处理）**
   - 批量操作
   - 脚本录制

## 优点

1. **降低耦合**: 调用者和接收者完全解耦
2. **容易扩展**: 增加新命令不影响现有代码
3. **支持撤销**: 可以实现命令的撤销和恢复
4. **支持组合**: 可以将多个命令组合成宏命令
5. **支持队列**: 可以将命令放入队列中依次执行
6. **支持日志**: 可以记录命令执行历史

## 缺点

1. **类数量增加**: 每个具体命令都需要一个类
2. **增加复杂度**: 引入了额外的抽象层

## 与其他模式的关系

- **责任链模式**: 命令可以在责任链中传递
- **备忘录模式**: 配合使用实现撤销功能
- **组合模式**: 宏命令使用组合模式
- **策略模式**: 都是将行为封装，但命令模式强调请求本身的封装

## 实际应用

- **Swing/AWT**: Action 接口
- **Spring**: CommandLineRunner
- **线程池**: Runnable/Callable
- **事件驱动**: 事件处理器
- **智能家居**: 遥控器控制各种设备
