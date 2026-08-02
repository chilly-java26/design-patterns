package com.designpatterns.adapter.logging;

public class LoggingDemo {
    public static void main(String[] args) {
        System.out.println("=== 日志框架适配器模式演示 ===\n");

        // 方式1: 手动指定使用 Log4j 适配器
        Logger log4jLogger = LoggerFactory.getLogger(LoggingDemo.class, Log4jAdapter.class);
        log4jLogger.info("使用 Log4j 记录日志");
        log4jLogger.error("Log4j 错误日志");

        System.out.println();

        // 方式2: 手动指定使用 JUL 适配器
        Logger julLogger = LoggerFactory.getLogger(LoggingDemo.class, JULAdapter.class);
        julLogger.info("使用 JUL 记录日志");
        julLogger.error("JUL 错误日志");

        System.out.println("\n✓ 可以手动选择使用哪个日志适配器！");
    }
}
