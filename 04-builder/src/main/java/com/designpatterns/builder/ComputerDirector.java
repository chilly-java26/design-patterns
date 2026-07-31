package com.designpatterns.builder;

/**
 * 指挥者：封装常见电脑配置方案
 * 提供便捷方法，一键生成预设配置
 */
public class ComputerDirector {
    
    /**
     * 构建游戏电脑
     * 高端 CPU + 大内存 + 高性能显卡
     */
    public static Computer buildGamingComputer() {
        return new Computer.Builder()
                .cpu("Intel Core i9-13900K")
                .ram("32GB DDR5")
                .storage("2TB NVMe SSD")
                .gpu("NVIDIA GeForce RTX 4090")
                .powerSupply("850W 80+ Gold")
                .pcCase("NZXT H510 Elite")
                .build();
    }
    
    /**
     * 构建办公电脑
     * 中端 CPU + 适中内存 + 集成显卡
     */
    public static Computer buildOfficeComputer() {
        return new Computer.Builder()
                .cpu("Intel Core i5-12400")
                .ram("16GB DDR4")
                .storage("512GB SSD")
                // 不设置显卡、电源和机箱，使用默认值
                .build();
    }
    
    /**
     * 构建服务器
     * 服务器级 CPU + 大内存 + 大硬盘 + 冗余电源
     */
    public static Computer buildServerComputer() {
        return new Computer.Builder()
                .cpu("AMD EPYC 7763 (64核)")
                .ram("128GB ECC DDR4")
                .storage("4TB NVMe SSD + 16TB HDD RAID")
                .powerSupply("1200W Redundant PSU")
                .pcCase("4U Rackmount Case")
                .build();
    }
    
    /**
     * 构建开发者工作站
     * 高性能 CPU + 大内存 + 专业显卡
     */
    public static Computer buildDeveloperWorkstation() {
        return new Computer.Builder()
                .cpu("AMD Ryzen 9 7950X")
                .ram("64GB DDR5")
                .storage("1TB NVMe SSD + 2TB SSD")
                .gpu("NVIDIA RTX 4070")
                .powerSupply("750W 80+ Platinum")
                .pcCase("Fractal Design Define 7")
                .build();
    }
    
    /**
     * 构建入门级电脑
     * 基础配置，适合日常使用
     */
    public static Computer buildBudgetComputer() {
        return new Computer.Builder()
                .cpu("Intel Core i3-12100")
                .ram("8GB DDR4")
                .storage("256GB SSD")
                .build();
    }
}
