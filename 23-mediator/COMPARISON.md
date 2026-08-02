# 中介者模式 vs 传统实现对比

## 代码对比

### 传统实现（高耦合）

```java
// ❌ 问题：组件需要互相引用
class TraditionalButton {
    private TraditionalInput nameInput;      // 依赖
    private TraditionalInput emailInput;     // 依赖
    private TraditionalLabel statusLabel;    // 依赖
    
    public void click() {
        // ❌ 逻辑分散：验证逻辑在按钮里
        if (nameInput.getValue().isEmpty() || emailInput.getValue().isEmpty()) {
            statusLabel.setText("请填写完整");
        } else {
            // 保存逻辑
            nameInput.disable();    // 直接操作其他组件
            emailInput.disable();   // 直接操作其他组件
        }
    }
}

class TraditionalInput {
    private TraditionalButton submitButton;  // 依赖
    private TraditionalInput emailInput;     // 依赖（姓名框需要知道邮箱框）
    private TraditionalLabel statusLabel;    // 依赖
    
    public void setValue(String value) {
        this.value = value;
        
        // ❌ 逻辑分散：验证逻辑也在输入框里
        // ❌ 需要检查另一个输入框的状态
        if (!value.isEmpty() && emailInput.getValue().isEmpty()) {
            submitButton.enable();
            statusLabel.setText("可以提交");
        }
    }
}
```

**依赖关系**：
```
Button ←────→ Input
  ↕              ↕
Label ←────────→ Email
```
形成网状依赖，难以维护。

---

### 中介者模式（低耦合）

```java
// ✅ 优势：组件只依赖中介者接口
class Button extends Component {
    public void click() {
        System.out.println("按钮被点击");
        // ✅ 只通知中介者，不知道其他组件
        mediator.notify(this, "submit_clicked");
    }
}

class Input extends Component {
    public void setValue(String value) {
        this.value = value;
        System.out.println("输入: " + value);
        // ✅ 只通知中介者，不知道其他组件
        mediator.notify(this, "input_changed");
    }
}

// ✅ 所有协调逻辑集中在中介者
class UserFormDialog implements DialogMediator {
    private Button submitButton;
    private Input nameInput;
    private Input emailInput;
    private Label statusLabel;
    private UserModel userModel;
    
    @Override
    public void notify(Component sender, String event) {
        if (event.equals("input_changed")) {
            // ✅ 集中处理：View → Model → View
            syncViewToModel();
            updateViewState();
        } 
        else if (event.equals("submit_clicked")) {
            handleSubmit();
        }
    }
    
    private void updateViewState() {
        // ✅ 集中的验证和协调逻辑
        if (userModel.isValid()) {
            submitButton.enable();
            statusLabel.setText("可以提交");
        } else {
            submitButton.disable();
            statusLabel.setText("请完善信息");
        }
    }
}
```

**依赖关系**：
```
Button ──┐
         │
Input ───┼───→ Mediator ←──→ Model
         │
Label ───┘
```
星型依赖，清晰简洁。

## 详细对比表

| 对比维度 | 传统实现 | 中介者模式 | 优势方 |
|---------|---------|-----------|-------|
| **组件耦合度** | 高（组件互相引用） | 低（只依赖中介者） | ✅ 中介者 |
| **逻辑位置** | 分散在各个组件 | 集中在中介者 | ✅ 中介者 |
| **代码可读性** | 难以理解整体流程 | 清晰的协调逻辑 | ✅ 中介者 |
| **新增组件** | 需修改多个类 | 只修改中介者 | ✅ 中介者 |
| **修改交互** | 需改多处代码 | 只改中介者 | ✅ 中介者 |
| **测试难度** | 高（需 mock 多个组件） | 中（只需 mock 中介者） | ✅ 中介者 |
| **组件复用** | 难（带着依赖） | 易（独立组件） | ✅ 中介者 |
| **调试难度** | 中（逻辑分散） | 中（需理解中介者） | ⚖️ 相当 |
| **代码总量** | 较少 | 稍多（多了中介者） | ❌ 传统 |
| **理解成本** | 低（直观） | 中（需理解模式） | ❌ 传统 |

## 场景分析

### 场景 1：新增一个"密码"输入框

#### 传统实现需要改动：
```
1. 创建 PasswordInput 类
2. 修改 SubmitButton（添加 passwordInput 引用和验证逻辑）
3. 修改 NameInput（添加 passwordInput 引用和验证逻辑）
4. 修改 EmailInput（添加 passwordInput 引用和验证逻辑）
5. 修改 main 方法（建立新的引用关系）

需要修改 5 处代码！
```

#### 中介者模式需要改动：
```
1. 创建 PasswordInput 类（继承 Component）
2. 修改 UserFormDialog（添加 passwordInput 引用和协调逻辑）
3. 修改 UserModel（添加 password 字段和验证）

只需修改 3 处代码！且改动集中！
```

---

### 场景 2：修改验证规则（邮箱必须包含 "@" 和 "."）

#### 传统实现需要改动：
```
1. 修改 SubmitButton 的验证逻辑
2. 修改 EmailInput 的验证逻辑
3. 可能还需要修改 NameInput 的验证逻辑

需要在多个类中重复修改！
```

#### 中介者模式需要改动：
```
1. 修改 UserModel 的 isValid() 方法

只需修改 1 处代码！
```

---

### 场景 3：单元测试提交功能

#### 传统实现：
```java
@Test
public void testSubmit() {
    // ❌ 需要 mock 所有依赖
    TraditionalInput nameInput = mock(TraditionalInput.class);
    TraditionalInput emailInput = mock(TraditionalInput.class);
    TraditionalLabel statusLabel = mock(TraditionalLabel.class);
    
    TraditionalButton button = new TraditionalButton("Submit");
    button.setNameInput(nameInput);      // 设置依赖
    button.setEmailInput(emailInput);    // 设置依赖
    button.setStatusLabel(statusLabel);  // 设置依赖
    
    when(nameInput.getValue()).thenReturn("张三");
    when(emailInput.getValue()).thenReturn("test@example.com");
    
    button.click();
    
    // 验证...
}
```

#### 中介者模式：
```java
@Test
public void testSubmit() {
    // ✅ 只需测试中介者的协调逻辑
    UserFormDialog dialog = new UserFormDialog();
    
    // Mock View 组件
    Button submitBtn = mock(Button.class);
    Input nameInput = mock(Input.class);
    Input emailInput = mock(Input.class);
    Label statusLabel = mock(Label.class);
    
    when(nameInput.getValue()).thenReturn("张三");
    when(emailInput.getValue()).thenReturn("test@example.com");
    
    dialog.registerComponents(submitBtn, null, nameInput, emailInput, statusLabel);
    dialog.notify(submitBtn, "submit_clicked");
    
    // 验证中介者是否正确协调
    verify(statusLabel).setText(contains("成功"));
}
```

## 实际运行对比

### 传统实现运行输出

```bash
$ mvn exec:java -Dexec.mainClass="com.designpatterns.mediator.traditional.TraditionalDemo"

========== 传统实现方式（无中介者）==========

[Submit] 按钮禁用
[状态] 请填写表单

=== 场景1：用户输入姓名 ===
[姓名] 输入: 张三
[Submit] 按钮禁用
[状态] ✗ 请完善信息

=== 场景2：用户输入邮箱 ===
[邮箱] 输入: zhangsan@example.com
[Submit] 按钮启用
[状态] ✓ 可以提交

【传统方式的问题】
❌ 1. 组件之间互相引用，形成网状依赖
❌ 2. 逻辑分散，难以理解整体交互流程
❌ 3. 新增组件需要修改多个类
❌ 4. 测试困难，需要 mock 多个依赖
```

### 中介者模式运行输出

```bash
$ mvn exec:java -Dexec.mainClass="com.designpatterns.mediator.MediatorDemo"

========== 中介者模式：MVC 用户表单对话框 ==========

[Submit] 按钮禁用
[状态] 请填写表单

=== 场景1：用户开始填写表单 ===
[姓名] 输入: 张三
[Submit] 按钮禁用
[状态] ✗ 请完善信息

=== 场景2：用户填写邮箱（格式错误）===
[邮箱] 输入: zhangsan
[Submit] 按钮禁用
[状态] ✗ 请完善信息

=== 场景3：用户修正邮箱 ===
[邮箱] 输入: zhangsan@example.com
[Submit] 按钮启用
[状态] ✓ 可以提交

【中介者模式总结】
1. View 组件（Button/Input/Label）之间完全解耦
2. 所有交互逻辑集中在 UserFormDialog（中介者）
3. 组件只通过中介者通信，不直接引用其他组件
4. 符合 MVC 架构：Controller 作为中介者协调 Model 和 View
```

## 何时使用中介者模式？

### ✅ 适合使用的场景

1. **多个对象之间有复杂的交互关系**
   - 例如：GUI 对话框、工作流系统

2. **对象间的依赖关系混乱，难以理解**
   - 例如：多个组件互相调用

3. **需要集中控制对象间的交互逻辑**
   - 例如：聊天室服务器、事件总线

4. **希望复用组件但不希望它们紧耦合**
   - 例如：UI 组件库

### ❌ 不适合使用的场景

1. **简单的两个对象交互**
   - 过度设计，增加复杂度

2. **需要明确控制流的场景**
   - 中介者会隐藏交互细节

3. **性能要求极高的场景**
   - 中介者会增加一层间接调用

## 结论

中介者模式通过引入中介者对象，将复杂的网状依赖转变为清晰的星型依赖，**显著降低了组件间的耦合度，提高了系统的可维护性和可扩展性**。

虽然会增加一定的代码量和理解成本，但在复杂的交互场景中，这些成本是值得的。特别是在 MVC 架构中，Controller 天然就是中介者角色，使用中介者模式是非常自然的选择。

**核心权衡**：
- 用一个中介者的复杂度，换取整个系统的解耦
- 用显式的协调逻辑，替代分散的隐式依赖
