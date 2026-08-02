package com.designpatterns.pipeline.model;

import java.time.LocalDateTime;

/**
 * 积分实体
 */
public class Score {
    private Long id;
    private Long userId;
    private Integer points;
    private String source;
    private LocalDateTime createdAt;

    public Score(Long userId, Integer points, String source) {
        this.userId = userId;
        this.points = points;
        this.source = source;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", userId=" + userId +
                ", points=" + points +
                ", source='" + source + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
