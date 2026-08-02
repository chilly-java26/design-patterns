package com.designpatterns.command;

/**
 * 调用者：遥控器
 * 负责调用命令对象执行请求
 */
public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;  // 记录上一次执行的命令
    
    public RemoteControl() {
        onCommands = new Command[7];  // 7个插槽
        offCommands = new Command[7];
        
        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }
    
    /**
     * 设置某个插槽的命令
     */
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }
    
    /**
     * 按下开按钮
     */
    public void onButtonPressed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }
    
    /**
     * 按下关按钮
     */
    public void offButtonPressed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }
    
    /**
     * 按下撤销按钮
     */
    public void undoButtonPressed() {
        undoCommand.undo();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n------ 遥控器 ------\n");
        for (int i = 0; i < onCommands.length; i++) {
            sb.append("[插槽 ").append(i).append("] ")
              .append(onCommands[i].getClass().getSimpleName())
              .append("    ")
              .append(offCommands[i].getClass().getSimpleName())
              .append("\n");
        }
        sb.append("[撤销] ").append(undoCommand.getClass().getSimpleName()).append("\n");
        return sb.toString();
    }
}
