package com.designpatterns.facade;

// 子系统：电视
public class TV {
    public void on() {
        System.out.println("📺 电视已开启");
    }

    public void off() {
        System.out.println("📺 电视已关闭");
    }

    public void setChannel(int channel) {
        System.out.println("📺 切换到频道 " + channel);
    }
}
