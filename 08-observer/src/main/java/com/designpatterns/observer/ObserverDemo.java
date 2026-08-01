package com.designpatterns.observer;

/**
 * 观察者模式演示
 * 场景：微信公众号订阅推送
 */
public class ObserverDemo {
    
    public static void main(String[] args) {
        System.out.println("========== 观察者模式：微信公众号示例 ==========\n");
        
        // 创建公众号（被观察者）
        WeChatAccount techAccount = new WeChatAccount("技术公众号");
        
        // 创建用户（观察者）
        User zhangsan = new User("张三");
        User lisi = new User("李四");
        User wangwu = new User("王五");
        
        // 用户关注公众号
        System.out.println("--- 用户关注阶段 ---");
        techAccount.attach(zhangsan);
        techAccount.attach(lisi);
        techAccount.attach(wangwu);
        
        // 公众号发布文章（自动通知所有粉丝）
        System.out.println("\n--- 发布文章阶段 ---");
        techAccount.publishArticle("Java设计模式详解");
        
        // 李四取消关注
        System.out.println("\n--- 取消关注阶段 ---");
        techAccount.detach(lisi);
        
        // 再次发布文章（只通知剩余粉丝）
        System.out.println("\n--- 再次发布阶段 ---");
        techAccount.publishArticle("Spring Boot实战教程");
        
        System.out.println("\n========== 示例结束 ==========");
    }
}
