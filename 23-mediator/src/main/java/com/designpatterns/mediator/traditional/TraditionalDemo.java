package com.designpatterns.mediator.traditional;

/**
 * 传统实现方式（没有中介者）
 * 
 * 问题演示：
 * 1. 组件之间高度耦合，互相持有引用
 * 2. 逻辑分散在各个组件中，难以维护
 * 3. 新增组件或修改交互需要改动多处代码
 */
public class TraditionalDemo {
    public static void main(String[] args) {
        System.out.println("========== 传统实现方式（无中介者）==========\n");
        
        // 创建组件
        TraditionalButton submitBtn = new TraditionalButton("Submit");
        TraditionalInput nameInput = new TraditionalInput("姓名");
        TraditionalInput emailInput = new TraditionalInput("邮箱");
        TraditionalLabel statusLabel = new TraditionalLabel();
        
        // ❌ 问题：需要手动建立组件之间的引用关系
        submitBtn.setNameInput(nameInput);
        submitBtn.setEmailInput(emailInput);
        submitBtn.setStatusLabel(statusLabel);
        
        nameInput.setSubmitButton(submitBtn);
        nameInput.setEmailInput(emailInput);
        nameInput.setStatusLabel(statusLabel);
        
        emailInput.setSubmitButton(submitBtn);
        emailInput.setNameInput(nameInput);
        emailInput.setStatusLabel(statusLabel);
        
        // 初始化
        submitBtn.disable();
        statusLabel.setText("请填写表单");
        
        System.out.println("\n=== 场景1：用户输入姓名 ===");
        nameInput.setValue("张三");
        
        System.out.println("\n=== 场景2：用户输入邮箱 ===");
        emailInput.setValue("zhangsan@example.com");
        
        System.out.println("\n=== 场景3：点击提交 ===");
        submitBtn.click();
        
        System.out.println("\n========== 演示结束 ==========");
        
        System.out.println("\n【传统方式的问题】");
        System.out.println("❌ 1. 组件之间互相引用，形成网状依赖");
        System.out.println("❌ 2. 逻辑分散，难以理解整体交互流程");
        System.out.println("❌ 3. 新增组件需要修改多个类");
        System.out.println("❌ 4. 测试困难，需要 mock 多个依赖");
    }
}

/**
 * 传统按钮实现
 * ❌ 问题：需要知道其他所有组件
 */
class TraditionalButton {
    private String label;
    private boolean enabled = true;
    
    // ❌ 高耦合：需要持有其他组件的引用
    private TraditionalInput nameInput;
    private TraditionalInput emailInput;
    private TraditionalLabel statusLabel;
    
    public TraditionalButton(String label) {
        this.label = label;
    }
    
    public void setNameInput(TraditionalInput nameInput) {
        this.nameInput = nameInput;
    }
    
    public void setEmailInput(TraditionalInput emailInput) {
        this.emailInput = emailInput;
    }
    
    public void setStatusLabel(TraditionalLabel statusLabel) {
        this.statusLabel = statusLabel;
    }
    
    public void click() {
        if (!enabled) {
            System.out.println("[" + label + "] 按钮已禁用");
            return;
        }
        
        System.out.println("[" + label + "] 按钮被点击");
        
        // ❌ 逻辑分散：验证逻辑在按钮里
        if (nameInput.getValue().isEmpty() || emailInput.getValue().isEmpty()) {
            statusLabel.setText("✗ 请填写完整");
        } else {
            // 模拟保存
            System.out.println("【保存】姓名=" + nameInput.getValue() + ", 邮箱=" + emailInput.getValue());
            statusLabel.setText("✓ 保存成功");
            
            // ❌ 需要直接操作其他组件
            nameInput.disable();
            emailInput.disable();
            this.disable();
        }
    }
    
    public void enable() {
        enabled = true;
        System.out.println("[" + label + "] 按钮启用");
    }
    
    public void disable() {
        enabled = false;
        System.out.println("[" + label + "] 按钮禁用");
    }
}

/**
 * 传统输入框实现
 * ❌ 问题：需要知道其他所有组件
 */
class TraditionalInput {
    private String name;
    private String value = "";
    private boolean enabled = true;
    
    // ❌ 高耦合：需要持有其他组件的引用
    private TraditionalButton submitButton;
    private TraditionalInput emailInput;  // 姓名输入框需要知道邮箱输入框
    private TraditionalInput nameInput;   // 邮箱输入框需要知道姓名输入框
    private TraditionalLabel statusLabel;
    
    public TraditionalInput(String name) {
        this.name = name;
    }
    
    public void setSubmitButton(TraditionalButton submitButton) {
        this.submitButton = submitButton;
    }
    
    public void setEmailInput(TraditionalInput emailInput) {
        this.emailInput = emailInput;
    }
    
    public void setNameInput(TraditionalInput nameInput) {
        this.nameInput = nameInput;
    }
    
    public void setStatusLabel(TraditionalLabel statusLabel) {
        this.statusLabel = statusLabel;
    }
    
    public void setValue(String value) {
        if (!enabled) {
            System.out.println("[" + name + "] 已禁用，无法输入");
            return;
        }
        
        this.value = value;
        System.out.println("[" + name + "] 输入: " + value);
        
        // ❌ 逻辑分散：验证逻辑也在输入框里
        // ❌ 高耦合：需要检查另一个输入框的状态
        if (name.equals("姓名")) {
            if (!value.isEmpty() && emailInput != null && !emailInput.getValue().isEmpty()) {
                submitButton.enable();
                statusLabel.setText("✓ 可以提交");
            } else {
                submitButton.disable();
                statusLabel.setText("✗ 请完善信息");
            }
        } else if (name.equals("邮箱")) {
            if (!value.isEmpty() && nameInput != null && !nameInput.getValue().isEmpty()) {
                submitButton.enable();
                statusLabel.setText("✓ 可以提交");
            } else {
                submitButton.disable();
                statusLabel.setText("✗ 请完善信息");
            }
        }
    }
    
    public String getValue() {
        return value;
    }
    
    public void disable() {
        enabled = false;
        System.out.println("[" + name + "] 输入框已禁用");
    }
}

/**
 * 传统标签实现
 */
class TraditionalLabel {
    private String text = "";
    
    public void setText(String text) {
        this.text = text;
        System.out.println("[状态] " + text);
    }
}
