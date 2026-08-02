package com.designpatterns.facade;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 门面模式演示：智能家居系统 ===");

        // 使用门面，客户端无需了解子系统细节
        SmartHomeFacade smartHome = new SmartHomeFacade();

        // 一键操作，内部协调多个子系统
        smartHome.arriveHome();
        
        smartHome.movieMode();
        
        smartHome.leaveHome();

        System.out.println("✓ 门面模式隐藏了复杂的子系统操作");
    }
}
