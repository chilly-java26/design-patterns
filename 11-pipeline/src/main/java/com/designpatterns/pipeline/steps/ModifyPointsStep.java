package com.designpatterns.pipeline.steps;

import com.designpatterns.pipeline.PipelineStep;
import com.designpatterns.pipeline.model.PipelineContext;
import com.designpatterns.pipeline.model.Score;
import com.designpatterns.pipeline.model.User;

/**
 * 步骤2: 修改用户积分
 */
public class ModifyPointsStep implements PipelineStep {
    private final Integer pointsToAdd;
    private final String source;

    public ModifyPointsStep(Integer pointsToAdd, String source) {
        this.pointsToAdd = pointsToAdd;
        this.source = source;
    }

    @Override
    public void execute(PipelineContext context) {
        User user = context.getUser();
        
        if (user == null) {
            context.markFailed("用户信息为空，无法修改积分");
            return;
        }
        
        Integer oldPoints = user.getPoints();
        Integer newPoints = oldPoints + pointsToAdd;
        
        System.out.println("  → 修改用户积分");
        System.out.println("  → 用户: " + user.getName());
        System.out.println("  → 原积分: " + oldPoints);
        System.out.println("  → 变化: " + (pointsToAdd > 0 ? "+" : "") + pointsToAdd);
        System.out.println("  → 新积分: " + newPoints);
        
        user.setPoints(newPoints);
        
        // 创建积分记录
        Score score = new Score(user.getId(), pointsToAdd, source);
        context.setScore(score);
    }

    @Override
    public String getStepName() {
        return "修改用户积分";
    }
}
