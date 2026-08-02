package com.designpatterns.adapter;

public class Main {
    public static void main(String[] args) {
        // 场景：中国用户需要220V电压
        System.out.println("=== 适配器模式演示 ===\n");

        // 有一个美国插头（只能提供110V）
        USPlug usPlug = new USPlug();

        // 使用适配器转换
        ChineseSocket adapter = new PowerAdapter(usPlug);
        adapter.provide220VPower();

        System.out.println("\n✓ 通过适配器成功使用美国插头！");
    }
}
