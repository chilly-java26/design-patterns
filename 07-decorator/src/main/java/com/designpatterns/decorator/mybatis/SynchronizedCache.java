package com.designpatterns.decorator.mybatis;

/**
 * 同步缓存装饰器 (ConcreteDecorator)
 * 模拟 MyBatis 的 SynchronizedCache - 添加线程安全功能
 */
public class SynchronizedCache implements Cache {
    
    // 被装饰的缓存
    private final Cache delegate;
    
    public SynchronizedCache(Cache delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public String getId() {
        return delegate.getId();
    }
    
    @Override
    public synchronized void putObject(Object key, Object value) {
        System.out.println("[SynchronizedCache] 加锁存入");
        delegate.putObject(key, value);
    }
    
    @Override
    public synchronized Object getObject(Object key) {
        System.out.println("[SynchronizedCache] 加锁读取");
        return delegate.getObject(key);
    }
    
    @Override
    public synchronized Object removeObject(Object key) {
        System.out.println("[SynchronizedCache] 加锁移除");
        return delegate.removeObject(key);
    }
    
    @Override
    public synchronized void clear() {
        System.out.println("[SynchronizedCache] 加锁清空");
        delegate.clear();
    }
    
    @Override
    public synchronized int getSize() {
        return delegate.getSize();
    }
}
