package com.designpatterns.adapter;

// 电源适配器（适配器）
public class PowerAdapter implements ChineseSocket {
    private USPlug usPlug;

    public PowerAdapter(USPlug usPlug) {
        this.usPlug = usPlug;
    }

    @Override
    public void provide220VPower() {
        usPlug.provideUSPower();
        System.out.println("适配器转换：110V → 220V");
    }
}
