package com.designpatterns.decorator.mybatis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU缓存装饰器 (ConcreteDecorator)
 * 模拟 MyBatis 的 LruCache - 添加最近最少使用淘汰策略
 */
public class LruCache implements Cache {
    
    // 被装饰的缓存
    private final Cache delegate;
    
    // 用于跟踪访问顺序的Map
    private Map<Object, Object> keyMap;
    
    // 最老的键（最久未使用）
    private Object eldestKey;
    
    // 缓存大小限制
    private int size = 100;
    
    public LruCache(Cache delegate) {
        this.delegate = delegate;
        setSize(size);
    }
    
    public void setSize(final int size) {
        this.size = size;
        // LinkedHashMap 的 accessOrder=true 表示按访问顺序排序
        keyMap = new LinkedHashMap<Object, Object>(size, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                boolean tooBig = size() > LruCache.this.size;
                if (tooBig) {
                    eldestKey = eldest.getKey();
                }
                return tooBig;
            }
        };
    }
    
    @Override
    public String getId() {
        return delegate.getId();
    }
    
    @Override
    public void putObject(Object key, Object value) {
        System.out.println("[LruCache] 准备存入: " + key);
        delegate.putObject(key, value);
        cycleKeyList(key);
    }
    
    @Override
    public Object getObject(Object key) {
        System.out.println("[LruCache] 准备读取: " + key);
        keyMap.get(key); // 触发访问，更新顺序
        return delegate.getObject(key);
    }
    
    @Override
    public Object removeObject(Object key) {
        System.out.println("[LruCache] 准备移除: " + key);
        return delegate.removeObject(key);
    }
    
    @Override
    public void clear() {
        System.out.println("[LruCache] 准备清空");
        delegate.clear();
        keyMap.clear();
    }
    
    @Override
    public int getSize() {
        return delegate.getSize();
    }
    
    private void cycleKeyList(Object key) {
        keyMap.put(key, key);
        if (eldestKey != null) {
            System.out.println("[LruCache] 缓存已满，淘汰最久未使用的: " + eldestKey);
            delegate.removeObject(eldestKey);
            eldestKey = null;
        }
    }
}
