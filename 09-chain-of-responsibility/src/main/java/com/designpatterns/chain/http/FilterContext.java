package com.designpatterns.chain.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 过滤器上下文
 * 在整个过滤器链中传递和共享数据
 * 类似于 ServletContext 或 Spring 的 ApplicationContext
 */
public class FilterContext {
    
    private Map<String, Object> attributes;
    
    public FilterContext() {
        this.attributes = new HashMap<>();
    }
    
    /**
     * 设置属性
     * @param key 属性键
     * @param value 属性值
     */
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
    
    /**
     * 获取属性
     * @param key 属性键
     * @return 属性值，不存在时返回null
     */
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
    
    /**
     * 获取属性（带类型转换）
     * @param key 属性键
     * @param type 期望的类型
     * @return 类型转换后的属性值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, Class<T> type) {
        Object value = attributes.get(key);
        if (value != null && type.isInstance(value)) {
            return (T) value;
        }
        return null;
    }
    
    /**
     * 移除属性
     * @param key 属性键
     * @return 被移除的属性值
     */
    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }
    
    /**
     * 检查属性是否存在
     * @param key 属性键
     * @return 是否存在
     */
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }
    
    /**
     * 清空所有属性
     */
    public void clear() {
        attributes.clear();
    }
    
    /**
     * 获取所有属性键
     */
    public java.util.Set<String> getAttributeNames() {
        return attributes.keySet();
    }
    
    @Override
    public String toString() {
        return "FilterContext{attributes=" + attributes + "}";
    }
}
