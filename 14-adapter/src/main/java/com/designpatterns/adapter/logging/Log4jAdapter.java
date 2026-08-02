package com.designpatterns.adapter.logging;

// Log4j 适配器
public class Log4jAdapter implements Logger {
    private Log4jLogger log4jLogger;

    public Log4jAdapter(String name) {
        this.log4jLogger = new Log4jLogger(name);
    }

    @Override
    public void info(String message) {
        log4jLogger.log("INFO", message);
    }

    @Override
    public void error(String message) {
        log4jLogger.log("ERROR", message);
    }
}
