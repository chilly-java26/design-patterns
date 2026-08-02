package com.designpatterns.adapter.logging;

// 统一日志接口（类似 SLF4J）
public interface Logger {
    void info(String message);
    void error(String message);
}
