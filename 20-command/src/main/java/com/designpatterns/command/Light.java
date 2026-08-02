package com.designpatterns.command;

/**
 * 接收者：电灯
 * 真正执行操作的对象
 */
public class Light {
    private String location;
    private boolean isOn = false;
    
    public Light(String location) {
        this.location = location;
    }
    
    public void on() {
        isOn = true;
        System.out.println(location + "的灯打开了");
    }
    
    public void off() {
        isOn = false;
        System.out.println(location + "的灯关闭了");
    }
    
    public boolean isOn() {
        return isOn;
    }
}
