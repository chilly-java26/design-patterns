package com.designpatterns.abstractfactory;

/**
 * 抽象工厂模式演示
 * 
 * 抽象工厂模式：提供一个接口，用于创建相关或依赖对象的家族，而不需要明确指定具体类
 */
public class AbstractFactoryDemo {
    
    /**
     * 渲染 UI（使用抽象工厂）
     * 
     * @param factory UI 工厂
     */
    private static void renderUI(UIFactory factory) {
        Button button = factory.createButton();
        TextField textField = factory.createTextField();
        
        button.render();
        textField.render();
    }
    
    public static void main(String[] args) {
        System.out.println("=== 抽象工厂模式演示 ===\n");
        
        // 使用 Windows 风格
        System.out.println("--- Windows 风格 ---");
        renderUI(WindowsUIFactory.INSTANCE);
        
        System.out.println();
        
        // 使用 Mac 风格
        System.out.println("--- Mac 风格 ---");
        renderUI(MacUIFactory.INSTANCE);
        
        System.out.println();
        
        // 切换主题只需切换工厂
        System.out.println("--- 优点演示 ---");
        System.out.println("✓ 保证产品族的一致性（同一工厂创建的组件风格统一）");
        System.out.println("✓ 易于切换产品族（只需切换工厂实例）");
        System.out.println("✓ 符合开闭原则（新增产品族不修改现有代码）");
    }
}
