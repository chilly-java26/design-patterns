package com.designpatterns.builder;

/**
 * 产品类：电脑
 * 使用建造者模式创建，属性不可变（Immutable）
 */
public class Computer {
    
    // ============ 产品属性（final，不可变）============
    private final String cpu;          // 必填
    private final String ram;          // 必填
    private final String storage;      // 必填
    private final String gpu;          // 可选
    private final String powerSupply;  // 可选
    private final String pcCase;       // 可选
    
    // ============ 私有构造函数 ============
    // 只能通过 Builder 创建
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.powerSupply = builder.powerSupply;
        this.pcCase = builder.pcCase;
    }
    
    // ============ Getter 方法（只读，无 Setter）============
    public String getCpu() {
        return cpu;
    }
    
    public String getRam() {
        return ram;
    }
    
    public String getStorage() {
        return storage;
    }
    
    public String getGpu() {
        return gpu;
    }
    
    public String getPowerSupply() {
        return powerSupply;
    }
    
    public String getPcCase() {
        return pcCase;
    }
    
    // ============ 显示信息 ============
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("========== 电脑配置 ==========\n");
        info.append("CPU:    ").append(cpu).append("\n");
        info.append("内存:   ").append(ram).append("\n");
        info.append("硬盘:   ").append(storage).append("\n");
        info.append("显卡:   ").append(gpu != null ? gpu : "集成显卡").append("\n");
        info.append("电源:   ").append(powerSupply != null ? powerSupply : "默认电源").append("\n");
        info.append("机箱:   ").append(pcCase != null ? pcCase : "默认机箱").append("\n");
        info.append("==============================");
        return info.toString();
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
    
    // ============================================================
    // ★ 静态内部类：Builder（建造者）
    // ============================================================
    public static class Builder {
        
        // ============ Builder 属性（可变）============
        private String cpu;          // 必填
        private String ram;          // 必填
        private String storage;      // 必填
        private String gpu;          // 可选
        private String powerSupply;  // 可选
        private String pcCase;       // 可选
        
        // ============ 链式调用方法 ============
        // 返回 this，支持 builder.cpu("i9").ram("32GB").build()
        
        /**
         * 设置 CPU（必填）
         */
        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }
        
        /**
         * 设置内存（必填）
         */
        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }
        
        /**
         * 设置硬盘（必填）
         */
        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }
        
        /**
         * 设置显卡（可选）
         */
        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        /**
         * 设置电源（可选）
         */
        public Builder powerSupply(String powerSupply) {
            this.powerSupply = powerSupply;
            return this;
        }
        
        /**
         * 设置机箱（可选）
         */
        public Builder pcCase(String pcCase) {
            this.pcCase = pcCase;
            return this;
        }
        
        // ============ 构建方法 ============
        /**
         * 验证必填项，然后创建 Computer 对象
         */
        public Computer build() {
            // 验证必填项
            if (cpu == null || cpu.trim().isEmpty()) {
                throw new IllegalStateException("CPU 是必填项，不能为空");
            }
            if (ram == null || ram.trim().isEmpty()) {
                throw new IllegalStateException("内存是必填项，不能为空");
            }
            if (storage == null || storage.trim().isEmpty()) {
                throw new IllegalStateException("硬盘是必填项，不能为空");
            }
            
            // 创建并返回 Computer 对象
            return new Computer(this);
        }
    }
}
