package com.designpatterns.mediator;

/**
 * 中介者模式演示
 * 
 * 场景：MVC 架构的用户表单对话框
 * - Model: UserModel（用户数据）
 * - View: Button, Input, Label（UI 组件）
 * - Controller: UserFormDialog（中介者，协调 Model 和 View）
 * 
 * 中介者模式的核心价值：
 * 1. View 组件之间完全解耦，互不认识
 * 2. 所有协调逻辑集中在 UserFormDialog 中
 * 3. 新增组件或修改交互规则只需修改 Controller
 */
public class MediatorDemo {
    public static void main(String[] args) {
        System.out.println("========== 中介者模式：MVC 用户表单对话框 ==========\n");
        
        // 创建中介者（Controller）
        UserFormDialog dialog = new UserFormDialog();
        
        // 创建 View 组件（都依赖中介者，但彼此不依赖）
        Button submitBtn = new Button(dialog, "Submit");
        Button cancelBtn = new Button(dialog, "Cancel");
        Input nameInput = new Input(dialog, "姓名");
        Input emailInput = new Input(dialog, "邮箱");
        Label statusLabel = new Label(dialog);
        
        // 将组件注册到中介者
        dialog.registerComponents(submitBtn, cancelBtn, 
                                  nameInput, emailInput, statusLabel);
        
        System.out.println("\n=== 场景1：用户开始填写表单 ===");
        nameInput.setValue("张三");
        
        System.out.println("\n=== 场景2：用户填写邮箱（格式错误）===");
        emailInput.setValue("zhangsan");  // 缺少 @ 和 .
        
        System.out.println("\n=== 场景3：用户修正邮箱 ===");
        emailInput.setValue("zhangsan@example.com");
        
        System.out.println("\n=== 场景4：用户点击提交 ===");
        submitBtn.click();
        
        System.out.println("\n=== 场景5：尝试继续输入（已禁用）===");
        nameInput.setValue("李四");
        
        System.out.println("\n=== 场景6：点击取消，重置表单 ===");
        cancelBtn.click();
        
        System.out.println("\n=== 场景7：重新填写表单 ===");
        nameInput.setValue("王五");
        emailInput.setValue("wangwu@test.com");
        
        System.out.println("\n=== 场景8：再次提交 ===");
        submitBtn.click();
        
        System.out.println("\n========== 演示结束 ==========");
        
        // 总结
        System.out.println("\n【中介者模式总结】");
        System.out.println("1. View 组件（Button/Input/Label）之间完全解耦");
        System.out.println("2. 所有交互逻辑集中在 UserFormDialog（中介者）");
        System.out.println("3. 组件只通过中介者通信，不直接引用其他组件");
        System.out.println("4. 符合 MVC 架构：Controller 作为中介者协调 Model 和 View");
    }
}
