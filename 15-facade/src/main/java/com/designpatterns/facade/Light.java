package com.designpatterns.facade;

// 子系统：灯光
public class Light {
    public void on() {
        System.out.println("💡 灯光已打开");
    }

    public void off() {
        System.out.println("💡 灯光已关闭");
    }

    public void dim(int level) {
        System.out.println("💡 调节亮度到 " + level + "%");
    }
}
