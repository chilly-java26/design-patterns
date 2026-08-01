package com.designpatterns.decorator.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * 永久缓存 (ConcreteComponent)
 * 模拟 MyBatis 的 PerpetualCache - 基础缓存实现
 */
public class PerpetualCache implements Cache {
    
    private final String id;
    private final Map<Object, Object> cache = new HashMap<>();
    
    public PerpetualCache(String id) {
        this.id = id;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
        System.out.println("  [PerpetualCache] 存入缓存: " + key + " = " + value);
    }
    
    @Override
    public Object getObject(Object key) {
        Object value = cache.get(key);
        System.out.println("  [PerpetualCache] 读取缓存: " + key + " = " + value);
        return value;
    }
    
    @Override
    public Object removeObject(Object key) {
        Object removed = cache.remove(key);
        System.out.println("  [PerpetualCache] 移除缓存: " + key);
        return removed;
    }
    
    @Override
    public void clear() {
        cache.clear();
        System.out.println("  [PerpetualCache] 清空缓存");
    }
    
    @Override
    public int getSize() {
        return cache.size();
    }
}
