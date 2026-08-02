package com.designpatterns.command;

/**
 * 命令接口
 * 定义命令的执行和撤销操作
 */
public interface Command {
    /**
     * 执行命令
     */
    void execute();
    
    /**
     * 撤销命令
     */
    void undo();
}
