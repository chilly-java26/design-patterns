package com.designpatterns.decorator.mybatis;

/**
 * 日志缓存装饰器 (ConcreteDecorator)
 * 模拟 MyBatis 的 LoggingCache - 添加日志统计功能
 */
public class LoggingCache implements Cache {
    
    // 被装饰的缓存
    private final Cache delegate;
    
    // 统计信息
    private int requests = 0;
    private int hits = 0;
    
    public LoggingCache(Cache delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public String getId() {
        return delegate.getId();
    }
    
    @Override
    public void putObject(Object key, Object value) {
        delegate.putObject(key, value);
    }
    
    @Override
    public Object getObject(Object key) {
        requests++;
        Object value = delegate.getObject(key);
        if (value != null) {
            hits++;
        }
        System.out.println("[LoggingCache] 缓存统计 - 请求次数: " + requests 
                         + ", 命中次数: " + hits 
                         + ", 命中率: " + getHitRatio() + "%");
        return value;
    }
    
    @Override
    public Object removeObject(Object key) {
        return delegate.removeObject(key);
    }
    
    @Override
    public void clear() {
        requests = 0;
        hits = 0;
        delegate.clear();
    }
    
    @Override
    public int getSize() {
        return delegate.getSize();
    }
    
    private double getHitRatio() {
        if (requests == 0) {
            return 0.0;
        }
        return (double) hits / requests * 100;
    }
}
