package com.designpatterns.pipeline;

import com.designpatterns.pipeline.model.PipelineContext;

/**
 * 管道步骤接口
 */
public interface PipelineStep {
    /**
     * 执行当前步骤
     * @param context 管道上下文
     */
    void execute(PipelineContext context);
    
    /**
     * 获取步骤名称
     * @return 步骤名称
     */
    String getStepName();
}
