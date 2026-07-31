package com.designpatterns.singleton;

/**
 * 单例模式 - 枚举实现（最安全）
 * 天然防御反射和反序列化攻击
 */
public enum EnumSingleton {
    
    INSTANCE;
    
    // 可以添加字段
    private String data;
    
    // 构造函数
    EnumSingleton() {
        System.out.println("EnumSingleton 实例已创建");
        this.data = "枚举单例数据";
    }
    
    // 示例方法
    public void showMessage() {
        System.out.println("这是枚举单例模式：" + data);
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
}
