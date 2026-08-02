package com.designpatterns.command;

/**
 * 接收者：风扇
 */
public class Fan {
    private String location;
    private int speed = 0;  // 0=关闭, 1=低速, 2=中速, 3=高速
    
    public Fan(String location) {
        this.location = location;
    }
    
    public void high() {
        speed = 3;
        System.out.println(location + "的风扇设置为高速");
    }
    
    public void medium() {
        speed = 2;
        System.out.println(location + "的风扇设置为中速");
    }
    
    public void low() {
        speed = 1;
        System.out.println(location + "的风扇设置为低速");
    }
    
    public void off() {
        speed = 0;
        System.out.println(location + "的风扇关闭了");
    }
    
    public int getSpeed() {
        return speed;
    }
}
