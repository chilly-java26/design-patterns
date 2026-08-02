package com.designpatterns.memento;

/**
 * 发起人（Originator）：游戏角色
 * 负责创建快照和从快照恢复状态
 */
public class GameCharacter {
    private int level;
    private int hp;
    private int gold;
    private String location;

    public GameCharacter(int level, int hp, int gold, String location) {
        this.level = level;
        this.hp = hp;
        this.gold = gold;
        this.location = location;
    }

    // 业务方法：战斗
    public void fight() {
        System.out.println("⚔️  开始战斗...");
        hp -= 30;
        gold += 50;
        System.out.println("💥 战斗结束！HP-30, Gold+50");
    }

    // 业务方法：升级
    public void levelUp() {
        level++;
        hp = 100;
        System.out.println("🎉 升级了！等级：" + level + "，HP已恢复");
    }

    // 业务方法：移动
    public void moveTo(String newLocation) {
        this.location = newLocation;
        System.out.println("🚶 移动到：" + newLocation);
    }

    // 创建快照
    public GameSnapshot snapshot() {
        System.out.println("📸 创建快照...");
        return new GameSnapshot(level, hp, gold, location);
    }

    // 从快照恢复
    public void restore(GameSnapshot snapshot) {
        this.level = snapshot.getLevel();
        this.hp = snapshot.getHp();
        this.gold = snapshot.getGold();
        this.location = snapshot.getLocation();
        System.out.println("♻️  从快照恢复：" + snapshot);
    }

    public void showStatus() {
        System.out.println(String.format("👤 角色状态 - 等级:%d HP:%d 金币:%d 位置:%s", 
            level, hp, gold, location));
    }
}
