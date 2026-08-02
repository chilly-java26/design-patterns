package com.designpatterns.memento;

/**
 * 备忘录（Memento）：游戏快照
 * 存储游戏角色的状态快照，不可变对象
 */
public class GameSnapshot {
    private final int level;
    private final int hp;
    private final int gold;
    private final String location;

    public GameSnapshot(int level, int hp, int gold, String location) {
        this.level = level;
        this.hp = hp;
        this.gold = gold;
        this.location = location;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getGold() {
        return gold;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return String.format("GameSnapshot[level=%d, hp=%d, gold=%d, location=%s]", 
            level, hp, gold, location);
    }
}
