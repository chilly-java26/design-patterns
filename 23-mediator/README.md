# 中介者模式（Mediator Pattern）

## 一句话描述

**用一个中介对象封装一系列对象之间的交互，使对象之间不需要显式地相互引用，从而降低耦合。**

## 核心概念

中介者模式本质上是：**对象之间不直接通信，都通过一个"中间人"来协调**，就像聊天室里的服务器或机场的塔台调度员。

## 项目结构

```
23-mediator/
├── src/main/java/com/designpatterns/mediator/
│   ├── DialogMediator.java      # 中介者接口
│   ├── UserFormDialog.java      # 具体中介者（Controller）
│   ├── Component.java            # 组件基类
│   ├── Button.java               # 按钮组件（View）
│   ├── Input.java                # 输入框组件（View）
│   ├── Label.java                # 标签组件（View）
│   ├── UserModel.java            # 用户数据模型（Model）
│   └── MediatorDemo.java         # 演示程序
├── README.md                     # 本文件
└── TUTORIAL.md                   # 详细教程
```

## 角色划分

### MVC 架构中的角色

| 角色 | 职责 | 本例中的实现 |
|------|------|-------------|
| **Model (M)** | 数据和业务逻辑 | `UserModel` - 保存姓名、邮箱，验证数据有效性 |
| **View (V)** | UI 展示和用户交互 | `Button`, `Input`, `Label` - 显示界面、接收输入 |
| **Controller (C)** | 协调 M 和 V，处理逻辑 | `UserFormDialog` - 响应事件、更新 Model、刷新 View |

### 中介者模式中的角色

- **Mediator（中介者接口）**: `DialogMediator` - 定义组件通信协议
- **ConcreteMediator（具体中介者）**: `UserFormDialog` - 实现协调逻辑
- **Colleague（同事类）**: `Component` 及其子类 - 通过中介者通信的组件

## 运行示例

### 运行中介者模式实现

```bash
cd 23-mediator
mvn clean compile
mvn exec:java -Dexec.mainClass="com.designpatterns.mediator.MediatorDemo"
```

### 运行传统实现对比

```bash
mvn exec:java -Dexec.mainClass="com.designpatterns.mediator.traditional.TraditionalDemo"
```

通过对比两种实现，可以清楚地看到中介者模式的优势。

## 核心代码示例

### 1. 中介者接口
```java
public interface DialogMediator {
    void notify(Component sender, String event);
}
```

### 2. 具体中介者（Controller）
```java
public class UserFormDialog implements DialogMediator {
    private Button submitButton;
    private Input nameInput;
    private Input emailInput;
    private Label statusLabel;
    private UserModel userModel = new UserModel();
    
    @Override
    public void notify(Component sender, String event) {
        if (event.equals("input_changed")) {
            syncViewToModel();  // View → Model
            updateViewState();  // Model → View
        } 
        else if (event.equals("submit_clicked")) {
            handleSubmit();
        }
    }
}
```

### 3. View 组件（只认识中介者）
```java
public class Input extends Component {
    public void setValue(String value) {
        this.value = value;
        mediator.notify(this, "input_changed");  // 通知中介者
    }
}
```

## 优缺点分析

### ✅ 优点

1. **降低耦合**：组件之间不直接引用，只依赖中介者
2. **集中控制**：所有交互逻辑集中在中介者中，易于维护
3. **易于扩展**：新增组件或修改交互规则只需修改中介者
4. **符合单一职责**：每个组件只关心自己的职责

### ❌ 缺点

1. **中介者可能过于复杂**：所有逻辑集中可能导致中介者成为"上帝类"
2. **隐式行为**：组件间的交互不够直观，可能让调用者困惑
3. **调试困难**：交互链路经过中介者，增加了调试难度

## 适用场景

### ✅ 适合使用

1. **本身就是协调系统**：聊天室、GUI 事件总线
2. **业务规则就是联动**：工作流引擎、状态机
3. **框架内部实现**：Spring 事件机制、消息队列
4. **复杂 UI 交互**：对话框、表单验证

### ❌ 不适合使用

1. **简单业务逻辑**：过度设计
2. **需要明确控制流**：隐式行为让人困惑
3. **调试困难场景**：联动太多难追踪

## 实际应用示例

1. **Spring ApplicationEventPublisher**：事件发布订阅
2. **Java Swing/AWT**：GUI 事件处理机制
3. **消息队列**：Kafka、RabbitMQ
4. **聊天室系统**：用户之间通过服务器通信
5. **MVC 框架**：Controller 协调 Model 和 View

## 与其他模式的关系

- **观察者模式**：中介者是一对多的单向通信；观察者是多对多的发布订阅
- **外观模式**：外观提供简化接口但不协调子系统间交互；中介者协调对象间交互
- **命令模式**：可以结合使用，命令封装请求，中介者协调执行

## 关键要点

1. **显式使用**：让使用者清楚地知道使用中介者会触发联动
2. **避免上帝类**：中介者不应该包含所有业务逻辑
3. **合理分层**：复杂场景可以考虑多层中介者
4. **文档化**：明确记录中介者的协调规则
