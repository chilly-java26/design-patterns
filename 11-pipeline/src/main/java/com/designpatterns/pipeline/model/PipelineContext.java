package com.designpatterns.pipeline.model;

/**
 * 管道上下文，用于在各个步骤之间传递数据
 */
public class PipelineContext {
    private User user;
    private Score score;
    private boolean success;
    private String errorMessage;

    public PipelineContext() {
        this.success = true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void markFailed(String message) {
        this.success = false;
        this.errorMessage = message;
    }

    @Override
    public String toString() {
        return "PipelineContext{" +
                "user=" + user +
                ", score=" + score +
                ", success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
