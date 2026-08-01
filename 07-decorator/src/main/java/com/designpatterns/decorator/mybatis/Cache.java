package com.designpatterns.decorator.mybatis;

/**
 * 缓存接口 (Component)
 * 模拟 MyBatis 的 Cache 接口
 */
public interface Cache {
    /**
     * 获取缓存ID
     */
    String getId();
    
    /**
     * 存入缓存
     */
    void putObject(Object key, Object value);
    
    /**
     * 获取缓存
     */
    Object getObject(Object key);
    
    /**
     * 移除缓存
     */
    Object removeObject(Object key);
    
    /**
     * 清空缓存
     */
    void clear();
    
    /**
     * 获取缓存大小
     */
    int getSize();
}
