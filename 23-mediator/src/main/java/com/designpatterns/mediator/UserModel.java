package com.designpatterns.mediator;

/**
 * 用户数据模型（Model）
 * 负责数据存储和业务逻辑验证
 */
public class UserModel {
    private String name;
    private String email;
    
    /**
     * 验证用户数据是否有效
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() 
            && email != null && email.contains("@") && email.contains(".");
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }
}
