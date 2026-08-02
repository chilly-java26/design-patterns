package com.designpatterns.command;

/**
 * 具体命令：风扇高速
 */
public class FanHighCommand implements Command {
    private Fan fan;
    private int prevSpeed;
    
    public FanHighCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.high();
    }
    
    @Override
    public void undo() {
        // 恢复到之前的速度
        switch (prevSpeed) {
            case 3: fan.high(); break;
            case 2: fan.medium(); break;
            case 1: fan.low(); break;
            default: fan.off(); break;
        }
    }
}
