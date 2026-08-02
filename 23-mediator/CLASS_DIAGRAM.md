# 中介者模式类图

## UML 类图

```
┌─────────────────────────────┐
│   <<interface>>             │
│   DialogMediator            │
├─────────────────────────────┤
│ + notify(Component, String) │
└─────────────────────────────┘
           △
           │ implements
           │
┌──────────┴──────────────────────────────────────────┐
│            UserFormDialog                           │
│                 (Controller + Mediator)             │
├─────────────────────────────────────────────────────┤
│ - submitButton: Button                              │
│ - cancelButton: Button                              │
│ - nameInput: Input                                  │
│ - emailInput: Input                                 │
│ - statusLabel: Label                                │
│ - userModel: UserModel                              │
├─────────────────────────────────────────────────────┤
│ + registerComponents(...)                           │
│ + notify(Component, String)                         │
│ - handleInputChanged()                              │
│ - handleSubmit()                                    │
│ - handleCancel()                                    │
│ - syncViewToModel()                                 │
│ - updateViewState()                                 │
└─────────────────────────────────────────────────────┘
           │                              │
           │ has                          │ has
           ▼                              ▼
┌──────────────────────┐         ┌──────────────────┐
│   Component          │         │   UserModel      │
│   (View Base)        │         │   (Model)        │
├──────────────────────┤         ├──────────────────┤
│ # mediator: Mediator │         │ - name: String   │
├──────────────────────┤         │ - email: String  │
│ + Component(Mediator)│         ├──────────────────┤
└──────────────────────┘         │ + isValid()      │
           △                     │ + getName()      │
           │                     │ + setName()      │
           │ extends             │ + getEmail()     │
    ┌──────┴──────┬──────┐      │ + setEmail()     │
    │             │      │       └──────────────────┘
┌───┴───┐    ┌───┴──┐  ┌┴──────┐
│Button │    │Input │  │Label  │
├───────┤    ├──────┤  ├───────┤
│-label │    │-name │  │-text  │
│-enabled    │-value│  ├───────┤
├───────┤    │-enabled  │+setText()
│+click()│    ├──────┤  │+getText()
│+enable()   │+setValue()└───────┘
│+disable()  │+getValue()
└───────┘    │+clear()
             │+enable()
             │+disable()
             └──────┘
```

## 交互序列图

### 场景：用户输入邮箱

```
用户          Input         Mediator        Model      Button      Label
 │              │              │             │           │           │
 │─setValue()──>│              │             │           │           │
 │              │              │             │           │           │
 │              │──notify()───>│             │           │           │
 │              │  (input_changed)           │           │           │
 │              │              │             │           │           │
 │              │              │─setEmail()─>│           │           │
 │              │              │             │           │           │
 │              │              │<─isValid()──│           │           │
 │              │              │   (true)    │           │           │
 │              │              │             │           │           │
 │              │              │─────enable()────────────>│           │
 │              │              │                          │           │
 │              │              │─────setText("✓ 可以提交")──────────>│
 │              │              │                                      │
```

### 场景：点击提交按钮

```
用户        Button       Mediator        Model       Label
 │            │             │             │            │
 │──click()──>│             │             │            │
 │            │             │             │            │
 │            │──notify()──>│             │            │
 │            │  (submit_clicked)         │            │
 │            │             │             │            │
 │            │             │─isValid()──>│            │
 │            │             │<─(true)─────│            │
 │            │             │             │            │
 │            │             │─save(Model)─>│            │
 │            │             │             │            │
 │            │             │─setText("保存成功")───────>│
 │            │             │                          │
```

## 组件依赖关系

### 传统方式（高耦合）

```
    Button ←─────→ Input
      ↕              ↕
    Label ←────────→ Email
      ↕              ↕
   网状依赖，难以维护
```

### 中介者模式（低耦合）

```
    Button ──┐
             │
    Input ───┼───→ Mediator ←──→ Model
             │
    Label ───┘

星型依赖，清晰简洁
```

## MVC 架构映射

```
┌─────────────────────────────────────────────────┐
│                   MVC 架构                      │
├─────────────────────────────────────────────────┤
│                                                 │
│  Model (M)                                      │
│  └─ UserModel (数据 + 验证逻辑)                 │
│                                                 │
│  View (V)                                       │
│  ├─ Button (UI 组件)                            │
│  ├─ Input (UI 组件)                             │
│  └─ Label (UI 组件)                             │
│                                                 │
│  Controller (C) = Mediator                      │
│  └─ UserFormDialog (协调 M ↔ V)                 │
│                                                 │
│  数据流向：                                      │
│  View → Controller → Model (用户输入)            │
│  Model → Controller → View (状态更新)            │
│                                                 │
└─────────────────────────────────────────────────┘
```

## 核心设计原则

1. **迪米特法则（最少知识原则）**
   - 组件只与中介者交互，不了解其他组件

2. **单一职责原则**
   - Component：负责自己的 UI 展示和基本行为
   - UserModel：负责数据和业务逻辑
   - UserFormDialog：负责协调和控制流

3. **开闭原则**
   - 新增组件或修改交互，只需修改中介者
   - 组件本身对扩展开放，对修改封闭

4. **依赖倒置原则**
   - 组件依赖抽象的 DialogMediator 接口
   - 不依赖具体的中介者实现
