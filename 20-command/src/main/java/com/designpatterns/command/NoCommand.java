package com.designpatterns.command;

/**
 * 空命令（空对象模式）
 * 用于初始化，避免空指针
 */
public class NoCommand implements Command {
    @Override
    public void execute() {
        // 什么都不做
    }
    
    @Override
    public void undo() {
        // 什么都不做
    }
}
