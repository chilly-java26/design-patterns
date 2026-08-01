package com.designpatterns.observer;

/**
 * 用户/粉丝 (ConcreteObserver)
 * 观察者的具体实现
 */
public class User implements Observer {
    
    private String name;
    
    public User(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        System.out.println("  → " + name + " 收到推送：《" + message + "》");
    }
    
    public String getName() {
        return name;
    }
}
