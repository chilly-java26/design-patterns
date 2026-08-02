package com.designpatterns.adapter.logging;

// 第三方日志框架 Log4j（被适配者）
public class Log4jLogger {
    private String name;

    public Log4jLogger(String name) {
        this.name = name;
    }

    public void log(String level, String msg) {
        System.out.println("[Log4j] " + level + " - " + name + ": " + msg);
    }
}
