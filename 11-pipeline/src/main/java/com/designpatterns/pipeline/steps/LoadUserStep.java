package com.designpatterns.pipeline.steps;

import com.designpatterns.pipeline.PipelineStep;
import com.designpatterns.pipeline.model.PipelineContext;
import com.designpatterns.pipeline.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 步骤1: 从数据库读取用户信息
 */
public class LoadUserStep implements PipelineStep {
    // 模拟数据库
    private static final Map<Long, User> userDatabase = new HashMap<>();
    
    static {
        userDatabase.put(1L, new User(1L, "张三", "zhangsan@example.com", 100));
        userDatabase.put(2L, new User(2L, "李四", "lisi@example.com", 200));
        userDatabase.put(3L, new User(3L, "王五", "wangwu@example.com", 150));
    }
    
    private final Long userId;

    public LoadUserStep(Long userId) {
        this.userId = userId;
    }

    @Override
    public void execute(PipelineContext context) {
        System.out.println("  → 正在从数据库查询用户ID: " + userId);
        
        User user = userDatabase.get(userId);
        
        if (user == null) {
            context.markFailed("用户不存在: " + userId);
            return;
        }
        
        context.setUser(user);
        System.out.println("  → 成功加载用户: " + user);
    }

    @Override
    public String getStepName() {
        return "加载用户信息";
    }
}
