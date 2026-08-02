package com.designpatterns.command;

/**
 * 具体命令：关闭风扇
 */
public class FanOffCommand implements Command {
    private Fan fan;
    private int prevSpeed;
    
    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.off();
    }
    
    @Override
    public void undo() {
        switch (prevSpeed) {
            case 3: fan.high(); break;
            case 2: fan.medium(); break;
            case 1: fan.low(); break;
            default: fan.off(); break;
        }
    }
}
