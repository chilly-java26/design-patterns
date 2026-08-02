# 中介者模式详细教程

## 1. 问题背景

### 没有中介者的传统做法

假设我们有一个用户表单，包含多个 UI 组件：提交按钮、取消按钮、输入框、状态标签。

**传统实现的问题**：

```java
// 提交按钮需要认识输入框和标签
class SubmitButton {
    private NameInput nameInput;
    private EmailInput emailInput;
    private StatusLabel statusLabel;
    
    public void onClick() {
        if (nameInput.isEmpty() || emailInput.isEmpty()) {
            statusLabel.setText("请填写完整");
        } else {
            // 保存数据
            statusLabel.setText("保存成功");
        }
    }
}

// 输入框需要认识按钮和标签
class NameInput {
    private SubmitButton submitButton;
    private EmailInput emailInput;
    private StatusLabel statusLabel;
    
    public void onChange() {
        if (isEmpty() || emailInput.isEmpty()) {
            submitButton.disable();
            statusLabel.setText("请完善信息");
        } else {
            submitButton.enable();
            statusLabel.setText("可以提交");
        }
    }
}
```

**存在的问题**：

1. ❌ **高耦合**：每个组件都需要持有其他组件的引用
2. ❌ **难维护**：修改一个组件的交互逻辑，需要改动多个类
3. ❌ **难扩展**：新增组件需要修改多处代码
4. ❌ **网状依赖**：组件间形成复杂的引用关系

```
Button ←→ Input ←→ Label
   ↓        ↓        ↓
  复杂的网状依赖关系
```

## 2. 中介者模式的解决方案

### 核心思想

**引入一个中介者对象，所有组件只与中介者通信，不直接相互引用。**

```
Button → Mediator ← Input
              ↑
            Label
```

### 架构设计

在 MVC 架构中：
- **Model**：UserModel（数据和验证逻辑）
- **View**：Button, Input, Label（UI 组件）
- **Controller**：UserFormDialog（中介者，协调 M 和 V）

## 3. 实现步骤

### 步骤 1：定义中介者接口

```java
public interface DialogMediator {
    void notify(Component sender, String event);
}
```

**关键点**：
- 定义统一的通信协议
- 组件通过事件类型通知中介者

### 步骤 2：创建组件基类

```java
public abstract class Component {
    protected DialogMediator mediator;
    
    public Component(DialogMediator mediator) {
        this.mediator = mediator;
    }
}
```

**关键点**：
- 所有组件持有中介者引用
- 组件之间不相互引用

### 步骤 3：实现具体组件（View）

```java
public class Input extends Component {
    private String value = "";
    
    public void setValue(String value) {
        this.value = value;
        System.out.println("输入: " + value);
        // 核心：通知中介者，而不是直接调用其他组件
        mediator.notify(this, "input_changed");
    }
}

public class Button extends Component {
    public void click() {
        System.out.println("按钮被点击");
        // 核心：通知中介者
        mediator.notify(this, "submit_clicked");
    }
}
```

**关键点**：
- 组件只负责自己的职责
- 所有交互通过 `mediator.notify()` 通知中介者

### 步骤 4：创建数据模型（Model）

```java
public class UserModel {
    private String name;
    private String email;
    
    public boolean isValid() {
        return name != null && !name.isEmpty() 
            && email != null && email.contains("@");
    }
    
    // getters and setters
}
```

**关键点**：
- Model 只负责数据和业务逻辑
- 不依赖 View 和 Controller

### 步骤 5：实现具体中介者（Controller）

```java
public class UserFormDialog implements DialogMediator {
    // View 组件
    private Button submitButton;
    private Input nameInput;
    private Input emailInput;
    private Label statusLabel;
    
    // Model
    private UserModel userModel = new UserModel();
    
    @Override
    public void notify(Component sender, String event) {
        if (event.equals("input_changed")) {
            // 1. View → Model
            syncViewToModel();
            // 2. Model → View
            updateViewState();
        } 
        else if (event.equals("submit_clicked")) {
            handleSubmit();
        }
    }
    
    private void syncViewToModel() {
        userModel.setName(nameInput.getValue());
        userModel.setEmail(emailInput.getValue());
    }
    
    private void updateViewState() {
        if (userModel.isValid()) {
            submitButton.enable();
            statusLabel.setText("✓ 可以提交");
        } else {
            submitButton.disable();
            statusLabel.setText("✗ 请完善信息");
        }
    }
    
    private void handleSubmit() {
        if (userModel.isValid()) {
            saveUser(userModel);
            statusLabel.setText("保存成功");
        }
    }
}
```

**关键点**：
- 所有协调逻辑集中在中介者
- 负责 Model 和 View 之间的双向绑定
- 响应事件并协调各个组件

## 4. 使用示例

```java
public class MediatorDemo {
    public static void main(String[] args) {
        // 创建中介者
        UserFormDialog dialog = new UserFormDialog();
        
        // 创建组件（都依赖中介者）
        Button submitBtn = new Button(dialog, "Submit");
        Input nameInput = new Input(dialog, "姓名");
        Input emailInput = new Input(dialog, "邮箱");
        Label statusLabel = new Label(dialog);
        
        // 注册组件到中介者
        dialog.registerComponents(submitBtn, cancelBtn, 
                                  nameInput, emailInput, statusLabel);
        
        // 用户操作
        nameInput.setValue("张三");        // → 中介者协调 → 按钮禁用
        emailInput.setValue("zhang@.com"); // → 中介者协调 → 按钮启用
        submitBtn.click();                 // → 中介者协调 → 保存数据
    }
}
```

## 5. 执行流程分析

### 场景：用户输入姓名

```
1. 用户输入 "张三"
   ↓
2. nameInput.setValue("张三")
   ↓
3. nameInput 调用 mediator.notify(this, "input_changed")
   ↓
4. UserFormDialog 收到通知
   ↓
5. 执行 syncViewToModel()：将 View 数据同步到 Model
   ↓
6. 执行 updateViewState()：根据 Model 状态更新 View
   ↓
7. 发现邮箱未填，调用 submitButton.disable()
   ↓
8. 调用 statusLabel.setText("✗ 请完善信息")
```

**关键观察**：
- nameInput 不知道 submitButton 和 statusLabel 的存在
- 所有协调逻辑由 UserFormDialog 处理
- 组件之间完全解耦

## 6. 优势对比

### 传统方式 vs 中介者模式

| 对比项 | 传统方式 | 中介者模式 |
|--------|---------|-----------|
| 组件耦合 | 高（互相引用） | 低（只依赖中介者） |
| 代码位置 | 分散在各个组件 | 集中在中介者 |
| 新增组件 | 需修改多个类 | 只修改中介者 |
| 修改交互 | 需改多处代码 | 只改中介者 |
| 测试难度 | 高（需要 mock 多个组件） | 中（只需 mock 中介者） |

## 7. 注意事项

### ⚠️ 避免"上帝类"

**问题**：中介者可能变得过于庞大，包含所有逻辑。

**解决方案**：
1. 按功能拆分多个中介者
2. 使用策略模式封装复杂逻辑
3. 将验证逻辑放在 Model 中

### ⚠️ 让交互逻辑显式化

**问题**：用户调用 `nameInput.setValue()` 时，不知道会触发其他组件变化。

**解决方案**：
1. 通过命名清晰表达意图（如 `dialog.updateForm()`）
2. 在文档中明确说明联动关系
3. 提供配置开关控制联动行为

### ⚠️ 合理使用

**适合**：
- GUI 对话框、表单
- 工作流引擎
- 聊天室系统

**不适合**：
- 简单的两个对象交互
- 需要明确控制流的场景

## 8. 扩展场景

### 场景 1：新增验证规则

只需修改中介者：

```java
private void updateViewState() {
    if (userModel.isValid() && isNameLengthValid()) {
        submitButton.enable();
    } else {
        submitButton.disable();
    }
}

private boolean isNameLengthValid() {
    return userModel.getName().length() >= 2;
}
```

### 场景 2：新增组件（密码输入框）

1. 创建新组件（不影响现有组件）
2. 在中介者中注册
3. 在中介者中添加协调逻辑

## 9. 总结

### 核心要点

1. **解耦神器**：组件间不直接依赖，只通过中介者通信
2. **集中控制**：所有交互逻辑集中在中介者，便于维护
3. **符合 MVC**：Controller 天然是中介者角色
4. **权衡取舍**：简单场景不要过度设计

### 何时使用

- ✅ 多个对象之间有复杂的交互关系
- ✅ 对象间的依赖关系混乱，难以理解
- ✅ 需要集中控制对象间的交互逻辑
- ✅ 希望复用组件但不希望它们紧耦合

### 实现关键

1. 定义清晰的中介者接口
2. 组件只持有中介者引用
3. 所有交互逻辑集中在中介者
4. 通过事件类型区分不同的交互
