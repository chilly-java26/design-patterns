package com.designpatterns.adapter.logging;

// JUL 适配器
public class JULAdapter implements Logger {
    private JULLogger julLogger;

    public JULAdapter(String name) {
        this.julLogger = new JULLogger(name);
    }

    @Override
    public void info(String message) {
        julLogger.logInfo(message);
    }

    @Override
    public void error(String message) {
        julLogger.logSevere(message);
    }
}
