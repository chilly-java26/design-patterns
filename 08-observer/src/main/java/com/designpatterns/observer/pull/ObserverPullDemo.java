package com.designpatterns.observer.pull;

/**
 * 观察者模式 - 拉模式演示
 * 场景：微信公众号订阅推送（拉模式）
 * 
 * 拉模式特点：
 * 1. Subject只通知Observer有更新，不主动推送数据
 * 2. Observer收到通知后，主动从Subject拉取自己需要的数据
 * 3. 不同的Observer可以拉取不同的数据，按需获取
 */
public class ObserverPullDemo {
    
    public static void main(String[] args) {
        System.out.println("========== 观察者模式 - 拉模式示例 ==========\n");
        
        // 创建公众号（被观察者）
        WeChatAccountPull techAccount = new WeChatAccountPull("技术公众号");
        
        // 创建不同偏好的用户（观察者）
        // 张三：关心作者和统计数据
        UserPull zhangsan = new UserPull("张三", true, true);
        
        // 李四：只关心作者信息
        UserPull lisi = new UserPull("李四", true, false);
        
        // 王五：只关心统计数据
        UserPull wangwu = new UserPull("王五", false, true);
        
        // 用户关注公众号
        System.out.println("--- 用户关注阶段 ---");
        techAccount.attach(zhangsan);
        techAccount.attach(lisi);
        techAccount.attach(wangwu);
        
        // 公众号发布文章（通知所有粉丝）
        System.out.println("\n--- 发布文章阶段 ---");
        techAccount.publishArticle("Java设计模式详解", "小明", 5000, 888);
        
        // 李四取消关注
        System.out.println("--- 取消关注阶段 ---");
        techAccount.detach(lisi);
        
        // 再次发布文章
        System.out.println("\n--- 再次发布阶段 ---");
        techAccount.publishArticle("Spring Boot实战教程", "小红", 8000, 1520);
        
        System.out.println("========== 拉模式 vs 推模式对比 ==========");
        System.out.println("推模式：Subject主动推送所有数据给Observer");
        System.out.println("  优点：Observer实现简单");
        System.out.println("  缺点：可能推送Observer不需要的数据，浪费资源");
        System.out.println();
        System.out.println("拉模式：Observer主动从Subject拉取需要的数据");
        System.out.println("  优点：Observer按需获取，更灵活，节省资源");
        System.out.println("  缺点：Observer需要知道Subject的数据结构");
        System.out.println("\n========== 示例结束 ==========");
    }
}
