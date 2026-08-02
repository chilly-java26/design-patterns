package com.designpatterns.mediator;

/**
 * 用户表单对话框（Controller + Mediator）
 * 作为中介者协调各个 View 组件，同时负责 Model 和 View 之间的交互
 * 
 * 核心职责：
 * 1. 响应 View 组件的事件通知
 * 2. 将 View 的数据同步到 Model
 * 3. 根据 Model 的状态更新 View
 * 4. 协调各个 View 组件之间的交互（通过中介者模式）
 */
public class UserFormDialog implements DialogMediator {
    // View 组件
    private Button submitButton;
    private Button cancelButton;
    private Input nameInput;
    private Input emailInput;
    private Label statusLabel;
    
    // Model
    private UserModel userModel = new UserModel();
    
    /**
     * 注册所有组件到中介者
     */
    public void registerComponents(Button submit, Button cancel, 
                                   Input name, Input email, Label status) {
        this.submitButton = submit;
        this.cancelButton = cancel;
        this.nameInput = name;
        this.emailInput = email;
        this.statusLabel = status;
        
        // 初始化状态
        submitButton.disable();
        statusLabel.setText("请填写表单");
    }
    
    /**
     * 中介者的核心方法：处理组件发来的通知
     * 这里集中了所有的协调逻辑
     */
    @Override
    public void notify(Component sender, String event) {
        if (event.equals("input_changed")) {
            // 输入框内容变化：同步 View → Model，然后更新 UI 状态
            handleInputChanged();
        } 
        else if (event.equals("submit_clicked")) {
            // 提交按钮点击：验证并保存数据
            handleSubmit();
        }
        else if (event.equals("cancel_clicked")) {
            // 取消按钮点击：重置表单
            handleCancel();
        }
    }
    
    /**
     * 处理输入变化事件
     */
    private void handleInputChanged() {
        // 1. View → Model（数据绑定）
        syncViewToModel();
        
        // 2. 根据 Model 状态 → 更新 View
        updateViewState();
    }
    
    /**
     * 处理提交事件
     */
    private void handleSubmit() {
        // 确保数据最新
        syncViewToModel();
        
        if (userModel.isValid()) {
            // 模拟保存到数据库
            saveUser(userModel);
            statusLabel.setText("✓ 保存成功: " + userModel.toString());
            
            // 禁用所有输入，防止二次提交
            disableAllInputs();
        } else {
            statusLabel.setText("✗ 数据无效，请检查输入");
        }
    }
    
    /**
     * 处理取消事件
     */
    private void handleCancel() {
        // 重置 Model
        userModel = new UserModel();
        
        // 重置 View
        nameInput.clear();
        emailInput.clear();
        nameInput.enable();
        emailInput.enable();
        submitButton.disable();
        statusLabel.setText("表单已重置");
    }
    
    /**
     * 将 View 的数据同步到 Model
     */
    private void syncViewToModel() {
        userModel.setName(nameInput.getValue());
        userModel.setEmail(emailInput.getValue());
    }
    
    /**
     * 根据 Model 的状态更新 View
     */
    private void updateViewState() {
        if (userModel.isValid()) {
            submitButton.enable();
            statusLabel.setText("✓ 可以提交");
        } else {
            submitButton.disable();
            statusLabel.setText("✗ 请完善信息");
        }
    }
    
    /**
     * 保存用户数据（模拟数据库操作）
     */
    private void saveUser(UserModel user) {
        System.out.println("【数据层】保存用户到数据库: " + user);
    }
    
    /**
     * 禁用所有输入控件
     * 注意：取消按钮不禁用，允许用户重置表单
     */
    private void disableAllInputs() {
        nameInput.disable();
        emailInput.disable();
        submitButton.disable();
        // cancelButton 保持启用，允许用户重置表单
    }
}
