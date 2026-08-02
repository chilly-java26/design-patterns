package com.designpatterns.interpreter.sql;

/**
 * 用户实体类
 * 用于演示 SQL WHERE 条件过滤
 */
public class User {
    private String name;
    private int age;
    private String status;
    private int level;
    private boolean vip;

    public User(String name, int age, String status, int level, boolean vip) {
        this.name = name;
        this.age = age;
        this.status = status;
        this.level = level;
        this.vip = vip;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public int getLevel() {
        return level;
    }

    public boolean isVip() {
        return vip;
    }

    /**
     * 将用户对象转换为 Context
     */
    public Context toContext() {
        Context context = new Context();
        context.set("name", name);
        context.set("age", age);
        context.set("status", status);
        context.set("level", level);
        context.set("vip", vip);
        return context;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", status='" + status + '\'' +
                ", level=" + level +
                ", vip=" + vip +
                '}';
    }
}
