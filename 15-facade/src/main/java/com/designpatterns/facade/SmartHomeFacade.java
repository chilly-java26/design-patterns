package com.designpatterns.facade;

// 门面类 - 提供简单统一接口
public class SmartHomeFacade {
    private Light light;
    private TV tv;
    private AirConditioner ac;

    public SmartHomeFacade() {
        this.light = new Light();
        this.tv = new TV();
        this.ac = new AirConditioner();
    }

    // 一键回家模式
    public void arriveHome() {
        System.out.println("\n🏠 启动回家模式...");
        light.on();
        light.dim(80);
        ac.on();
        ac.setTemperature(24);
        tv.on();
        tv.setChannel(5);
        System.out.println("✅ 回家模式完成\n");
    }

    // 一键离家模式
    public void leaveHome() {
        System.out.println("\n🚪 启动离家模式...");
        tv.off();
        light.off();
        ac.off();
        System.out.println("✅ 离家模式完成\n");
    }

    // 一键观影模式
    public void movieMode() {
        System.out.println("\n🎬 启动观影模式...");
        light.dim(20);
        tv.on();
        tv.setChannel(10);
        ac.setTemperature(22);
        System.out.println("✅ 观影模式完成\n");
    }
}
