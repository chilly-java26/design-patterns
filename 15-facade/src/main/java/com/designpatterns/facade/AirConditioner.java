package com.designpatterns.facade;

// 子系统：空调
public class AirConditioner {
    public void on() {
        System.out.println("❄️ 空调已启动");
    }

    public void off() {
        System.out.println("❄️ 空调已关闭");
    }

    public void setTemperature(int temp) {
        System.out.println("❄️ 设置温度为 " + temp + "°C");
    }
}
