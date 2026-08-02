package com.designpatterns.pipeline;

import com.designpatterns.pipeline.model.PipelineContext;
import com.designpatterns.pipeline.model.Score;
import com.designpatterns.pipeline.steps.LoadUserStep;
import com.designpatterns.pipeline.steps.ModifyPointsStep;
import com.designpatterns.pipeline.steps.SaveScoreStep;

/**
 * 管道模式演示
 * 
 * 管道模式（Pipeline Pattern）是一种将复杂的处理过程分解为一系列独立步骤的设计模式。
 * 每个步骤专注于单一职责，步骤之间通过上下文对象传递数据。
 * 
 * 优点：
 * 1. 职责分离：每个步骤只负责一个具体任务
 * 2. 易于扩展：可以轻松添加、删除或重排步骤
 * 3. 可复用：步骤可以在不同的管道中重复使用
 * 4. 易于测试：每个步骤可以独立测试
 * 5. 灵活性：可以根据条件动态组合步骤
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println("       管道模式演示 - 用户积分系统");
        System.out.println("=======================================\n");

        // 示例1: 给用户增加积分
        System.out.println("\n【示例1】给用户增加积分");
        Pipeline addPointsPipeline = new Pipeline()
                .addStep(new LoadUserStep(1L))
                .addStep(new ModifyPointsStep(50, "每日签到"))
                .addStep(new SaveScoreStep());

        PipelineContext context1 = new PipelineContext();
        addPointsPipeline.execute(context1);

        // 示例2: 给另一个用户增加积分
        System.out.println("\n\n【示例2】给另一个用户增加积分");
        Pipeline addPointsPipeline2 = new Pipeline()
                .addStep(new LoadUserStep(2L))
                .addStep(new ModifyPointsStep(100, "完成任务"))
                .addStep(new SaveScoreStep());

        PipelineContext context2 = new PipelineContext();
        addPointsPipeline2.execute(context2);

        // 示例3: 扣除积分
        System.out.println("\n\n【示例3】扣除用户积分");
        Pipeline deductPointsPipeline = new Pipeline()
                .addStep(new LoadUserStep(1L))
                .addStep(new ModifyPointsStep(-20, "兑换商品"))
                .addStep(new SaveScoreStep());

        PipelineContext context3 = new PipelineContext();
        deductPointsPipeline.execute(context3);

        // 示例4: 处理不存在的用户（演示错误处理）
        System.out.println("\n\n【示例4】处理不存在的用户");
        Pipeline errorPipeline = new Pipeline()
                .addStep(new LoadUserStep(999L))
                .addStep(new ModifyPointsStep(50, "测试"))
                .addStep(new SaveScoreStep());

        PipelineContext context4 = new PipelineContext();
        errorPipeline.execute(context4);

        // 显示所有积分记录
        System.out.println("\n\n=======================================");
        System.out.println("         数据库中的积分记录");
        System.out.println("=======================================");
        for (Score score : SaveScoreStep.getAllScores()) {
            System.out.println(score);
        }
    }
}
