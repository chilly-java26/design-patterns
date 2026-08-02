package com.designpatterns.command;

/**
 * 命令模式演示
 * 
 * 核心思想：将"请求"封装成对象，从而解耦请求发送者和接收者
 * 
 * 角色：
 * - Command: 命令接口
 * - ConcreteCommand: 具体命令（LightOnCommand, FanHighCommand等）
 * - Receiver: 接收者，真正执行操作的对象（Light, Fan等）
 * - Invoker: 调用者（RemoteControl）
 * - Client: 客户端，创建具体命令并设置接收者
 * 
 * 优点：
 * 1. 解耦调用者和接收者
 * 2. 容易扩展新命令
 * 3. 可以实现命令的撤销和恢复
 * 4. 可以将多个命令组合成宏命令
 * 5. 可以实现命令队列和日志记录
 */
public class CommandDemo {
    public static void main(String[] args) {
        System.out.println("========== 命令模式演示 ==========\n");
        
        // 创建接收者
        Light livingRoomLight = new Light("客厅");
        Light bedroomLight = new Light("卧室");
        Fan livingRoomFan = new Fan("客厅");
        
        // 创建具体命令
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand bedroomLightOn = new LightOnCommand(bedroomLight);
        LightOffCommand bedroomLightOff = new LightOffCommand(bedroomLight);
        FanHighCommand livingRoomFanHigh = new FanHighCommand(livingRoomFan);
        FanOffCommand livingRoomFanOff = new FanOffCommand(livingRoomFan);
        
        // 创建调用者（遥控器）
        RemoteControl remote = new RemoteControl();
        
        // 设置命令
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, bedroomLightOn, bedroomLightOff);
        remote.setCommand(2, livingRoomFanHigh, livingRoomFanOff);
        
        System.out.println(remote);
        
        // 测试基本功能
        System.out.println("\n--- 测试基本功能 ---");
        remote.onButtonPressed(0);   // 打开客厅灯
        remote.offButtonPressed(0);  // 关闭客厅灯
        remote.onButtonPressed(1);   // 打开卧室灯
        remote.onButtonPressed(2);   // 打开客厅风扇高速
        
        // 测试撤销功能
        System.out.println("\n--- 测试撤销功能 ---");
        remote.undoButtonPressed();  // 撤销上一次操作（关闭风扇）
        
        remote.offButtonPressed(1);  // 关闭卧室灯
        remote.undoButtonPressed();  // 撤销（打开卧室灯）
        
        // 测试宏命令
        System.out.println("\n--- 测试宏命令（派对模式）---");
        Command[] partyOn = {livingRoomLightOn, bedroomLightOn, livingRoomFanHigh};
        Command[] partyOff = {livingRoomLightOff, bedroomLightOff, livingRoomFanOff};
        
        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partyOff);
        
        remote.setCommand(3, partyOnMacro, partyOffMacro);
        
        System.out.println("执行派对模式：");
        remote.onButtonPressed(3);
        
        System.out.println("\n关闭派对模式：");
        remote.offButtonPressed(3);
        
        System.out.println("\n撤销关闭派对模式：");
        remote.undoButtonPressed();
    }
}
