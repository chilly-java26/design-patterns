package com.designpatterns.builder;

/**
 * 建造者模式演示
 */
public class BuilderDemo {
    
    public static void main(String[] args) {
        
        System.out.println("=== 建造者模式演示 ===\n");
        
        // ========================================
        // 方式 1：手动构建（完全自定义）
        // ========================================
        System.out.println("【方式 1：手动构建 - 完全自定义配置】");
        Computer customPC = new Computer.Builder()
                .cpu("AMD Ryzen 9 7950X")
                .ram("64GB DDR5")
                .storage("1TB NVMe SSD")
                .gpu("AMD Radeon RX 7900 XTX")
                .powerSupply("750W 80+ Platinum")
                .pcCase("Fractal Design Meshify C")
                .build();
        System.out.println(customPC);
        System.out.println();
        
        // ========================================
        // 方式 2：使用 Director（预设配置）
        // ========================================
        System.out.println("【方式 2：使用 Director - 游戏电脑】");
        Computer gamingPC = ComputerDirector.buildGamingComputer();
        System.out.println(gamingPC);
        System.out.println();
        
        System.out.println("【方式 3：使用 Director - 办公电脑】");
        Computer officePC = ComputerDirector.buildOfficeComputer();
        System.out.println(officePC);
        System.out.println();
        
        System.out.println("【方式 4：使用 Director - 服务器】");
        Computer serverPC = ComputerDirector.buildServerComputer();
        System.out.println(serverPC);
        System.out.println();
        
        // ========================================
        // 演示链式调用的灵活性
        // ========================================
        System.out.println("【演示：链式调用 - 只设置必填项】");
        Computer minimalPC = new Computer.Builder()
                .cpu("Intel Core i7-12700")
                .ram("16GB")
                .storage("512GB SSD")
                .build();  // 可选项不设置，使用默认值
        System.out.println(minimalPC);
        System.out.println();
        
        // ========================================
        // 演示必填项验证
        // ========================================
        System.out.println("【演示：必填项验证】");
        try {
            Computer invalidPC = new Computer.Builder()
                    .cpu("Intel Core i7")
                    // 缺少 ram 和 storage
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("✗ 构建失败：" + e.getMessage());
        }
        
        try {
            Computer invalidPC2 = new Computer.Builder()
                    .cpu("Intel Core i7")
                    .ram("16GB")
                    // 缺少 storage
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("✗ 构建失败：" + e.getMessage());
        }
        
        System.out.println();
        
        // ========================================
        // 演示不可变性
        // ========================================
        System.out.println("【演示：不可变对象】");
        System.out.println("✓ Computer 对象一旦创建，属性不可修改");
        System.out.println("✓ 只有 getter 方法，没有 setter 方法");
        System.out.println("✓ 所有属性都是 final 的");
        System.out.println("✓ 线程安全，可以在多线程环境中安全使用");
        System.out.println();
        
        // ========================================
        // 优点总结
        // ========================================
        System.out.println("【建造者模式优点】");
        System.out.println("1. 参数清晰：builder.cpu(\"i9\") 比构造函数参数更易读");
        System.out.println("2. 链式调用：流畅的 API，代码优雅");
        System.out.println("3. 必填可选：明确哪些是必填，哪些是可选");
        System.out.println("4. 不可变对象：线程安全，防止被意外修改");
        System.out.println("5. 灵活扩展：新增属性不影响现有代码");
        System.out.println("6. Director 封装：常见配置一键生成");
    }
}
