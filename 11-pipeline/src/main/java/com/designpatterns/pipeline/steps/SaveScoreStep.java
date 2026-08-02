package com.designpatterns.pipeline.steps;

import com.designpatterns.pipeline.PipelineStep;
import com.designpatterns.pipeline.model.PipelineContext;
import com.designpatterns.pipeline.model.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 步骤3: 保存积分到数据库
 */
public class SaveScoreStep implements PipelineStep {
    // 模拟积分数据库
    private static final List<Score> scoreDatabase = new ArrayList<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public void execute(PipelineContext context) {
        Score score = context.getScore();
        
        if (score == null) {
            context.markFailed("积分信息为空，无法保存");
            return;
        }
        
        // 模拟保存到数据库
        score.setId(idGenerator.getAndIncrement());
        scoreDatabase.add(score);
        
        System.out.println("  → 保存积分记录到数据库");
        System.out.println("  → " + score);
        System.out.println("  → 当前数据库中积分记录总数: " + scoreDatabase.size());
    }

    @Override
    public String getStepName() {
        return "保存积分记录";
    }
    
    /**
     * 查询数据库中的所有积分记录（用于演示）
     */
    public static List<Score> getAllScores() {
        return new ArrayList<>(scoreDatabase);
    }
}
