package com.designpatterns.adapter.logging;

// 第三方日志框架 JUL - java.util.logging（被适配者）
public class JULLogger {
    private String loggerName;

    public JULLogger(String loggerName) {
        this.loggerName = loggerName;
    }

    public void logInfo(String message) {
        System.out.println("[JUL] INFO: " + loggerName + " - " + message);
    }

    public void logSevere(String message) {
        System.out.println("[JUL] SEVERE: " + loggerName + " - " + message);
    }
}
