package com.designpatterns.command;

/**
 * 宏命令（组合命令）
 * 可以一次执行多个命令
 */
public class MacroCommand implements Command {
    private Command[] commands;
    
    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }
    
    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        // 反向撤销所有命令
        for (int i = commands.length - 1; i >= 0; i--) {
            commands[i].undo();
        }
    }
}
