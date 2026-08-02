package com.designpatterns.pipeline;

import com.designpatterns.pipeline.model.PipelineContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 管道类，负责管理和执行一系列步骤
 */
public class Pipeline {
    private final List<PipelineStep> steps = new ArrayList<>();

    /**
     * 添加步骤到管道
     */
    public Pipeline addStep(PipelineStep step) {
        steps.add(step);
        return this;
    }

    /**
     * 执行管道中的所有步骤
     */
    public PipelineContext execute(PipelineContext context) {
        System.out.println("========== 开始执行管道 ==========");
        
        for (PipelineStep step : steps) {
            if (!context.isSuccess()) {
                System.out.println("管道执行失败，停止执行后续步骤");
                break;
            }
            
            System.out.println("\n执行步骤: " + step.getStepName());
            try {
                step.execute(context);
                System.out.println("步骤 [" + step.getStepName() + "] 执行成功");
            } catch (Exception e) {
                context.markFailed("步骤 [" + step.getStepName() + "] 执行失败: " + e.getMessage());
                System.out.println("步骤 [" + step.getStepName() + "] 执行失败: " + e.getMessage());
            }
        }
        
        System.out.println("\n========== 管道执行完成 ==========");
        System.out.println("执行结果: " + (context.isSuccess() ? "成功" : "失败"));
        if (!context.isSuccess()) {
            System.out.println("错误信息: " + context.getErrorMessage());
        }
        
        return context;
    }
}
