package com.designpatterns.observer.pull;

/**
 * 用户/粉丝 - 拉模式 (ConcreteObserver)
 * 观察者主动从Subject拉取自己关心的数据
 */
public class UserPull implements ObserverPull {
    
    private String name;
    private boolean interestedInAuthor;    // 是否关心作者信息
    private boolean interestedInStats;     // 是否关心统计数据
    
    public UserPull(String name, boolean interestedInAuthor, boolean interestedInStats) {
        this.name = name;
        this.interestedInAuthor = interestedInAuthor;
        this.interestedInStats = interestedInStats;
    }
    
    @Override
    public void update(SubjectPull subject) {
        // 拉模式：观察者主动从Subject拉取自己需要的数据
        if (subject instanceof WeChatAccountPull) {
            WeChatAccountPull account = (WeChatAccountPull) subject;
            
            // 每个观察者可以根据自己的需求拉取不同的数据
            System.out.println("  → " + name + " 收到通知，开始拉取数据：");
            System.out.println("     文章标题: 《" + account.getLatestArticle() + "》");
            
            // 只有关心作者的用户才拉取作者信息
            if (interestedInAuthor) {
                System.out.println("     作者: " + account.getAuthor());
            }
            
            // 只有关心统计的用户才拉取统计信息
            if (interestedInStats) {
                System.out.println("     阅读量: " + account.getReadCount() + " | 点赞数: " + account.getLikeCount());
            }
            
            System.out.println();
        }
    }
    
    public String getName() {
        return name;
    }
}
