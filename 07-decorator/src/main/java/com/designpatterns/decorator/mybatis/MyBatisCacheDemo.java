package com.designpatterns.decorator.mybatis;

/**
 * MyBatis 缓存装饰器演示
 * MyBatis 使用装饰器模式实现缓存的层层增强
 */
public class MyBatisCacheDemo {
    
    public static void main(String[] args) {
        System.out.println("========== MyBatis 缓存装饰器模式演示 ==========\n");
        
        // 演示1：基础缓存
        demonstrateBasicCache();
        
        System.out.println("\n" + getSeparator() + "\n");
        
        // 演示2：LRU缓存装饰
        demonstrateLruCache();
        
        System.out.println("\n" + getSeparator() + "\n");
        
        // 演示3：日志缓存装饰
        demonstrateLoggingCache();
        
        System.out.println("\n" + getSeparator() + "\n");
        
        // 演示4：多重装饰（MyBatis实际使用方式）
        demonstrateMultipleDecorators();
    }
    
    /**
     * 演示1：基础缓存
     */
    private static void demonstrateBasicCache() {
        System.out.println("【演示1：基础缓存 PerpetualCache】\n");
        
        Cache cache = new PerpetualCache("demo-cache");
        
        cache.putObject("user:1", "张三");
        cache.putObject("user:2", "李四");
        
        System.out.println("\n查询结果: " + cache.getObject("user:1"));
        System.out.println("查询结果: " + cache.getObject("user:2"));
    }
    
    /**
     * 演示2：LRU缓存装饰
     */
    private static void demonstrateLruCache() {
        System.out.println("【演示2：LRU缓存装饰器】\n");
        
        // 基础缓存 + LRU装饰
        Cache cache = new LruCache(new PerpetualCache("lru-cache"));
        ((LruCache) cache).setSize(2); // 只能存2个
        
        System.out.println("设置缓存大小为2，测试LRU淘汰策略：\n");
        
        cache.putObject("user:1", "张三");
        cache.putObject("user:2", "李四");
        System.out.println("\n当前缓存大小: " + cache.getSize());
        
        System.out.println("\n存入第3个元素，触发LRU淘汰：");
        cache.putObject("user:3", "王五");
        System.out.println("当前缓存大小: " + cache.getSize());
    }
    
    /**
     * 演示3：日志缓存装饰
     */
    private static void demonstrateLoggingCache() {
        System.out.println("【演示3：日志缓存装饰器】\n");
        
        // 基础缓存 + 日志装饰
        Cache cache = new LoggingCache(new PerpetualCache("logging-cache"));
        
        cache.putObject("user:1", "张三");
        cache.putObject("user:2", "李四");
        
        System.out.println("\n模拟查询请求：");
        cache.getObject("user:1"); // 命中
        cache.getObject("user:2"); // 命中
        cache.getObject("user:3"); // 未命中
        cache.getObject("user:1"); // 命中
    }
    
    /**
     * 演示4：多重装饰（模拟MyBatis实际使用）
     */
    private static void demonstrateMultipleDecorators() {
        System.out.println("【演示4：多重装饰 - 模拟MyBatis真实场景】\n");
        System.out.println("装饰链: SynchronizedCache -> LoggingCache -> LruCache -> PerpetualCache\n");
        
        // 基础缓存
        Cache cache = new PerpetualCache("mybatis-cache");
        
        // 第1层装饰：添加LRU淘汰策略
        cache = new LruCache(cache);
        ((LruCache) cache).setSize(3);
        
        // 第2层装饰：添加日志统计
        cache = new LoggingCache(cache);
        
        // 第3层装饰：添加线程安全
        cache = new SynchronizedCache(cache);
        
        System.out.println("存入数据：");
        cache.putObject("sql:1", "SELECT * FROM user WHERE id=1");
        
        System.out.println("\n查询数据：");
        cache.getObject("sql:1");
        
        System.out.println("\n再次查询（统计命中率）：");
        cache.getObject("sql:1");
        
        System.out.println("\n说明：");
        System.out.println("• 每个装饰器都添加了特定功能");
        System.out.println("• 装饰器可以任意组合，非常灵活");
        System.out.println("• MyBatis就是用这种方式构建缓存系统的");
    }
    
    private static String getSeparator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            sb.append("=");
        }
        return sb.toString();
    }
}
