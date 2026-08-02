package com.designpatterns.memento;

/**
 * 备忘录模式示例：游戏存档系统
 * 
 * 场景：
 * 玩家在游戏中可以随时保存进度，打BOSS前存档，失败后读档重来
 */
public class MementoDemo {
    public static void main(String[] args) {
        // 创建角色和存档系统
        GameCharacter player = new GameCharacter(1, 100, 0, "新手村");
        SaveSystem saveSystem = new SaveSystem();

        System.out.println("=== 游戏开始 ===\n");
        player.showStatus();
        System.out.println();

        // 场景1：存档1 - 新手村初始状态
        System.out.println("--- 场景1：在新手村保存进度 ---");
        saveSystem.saveToSlot("存档1-新手村", player.snapshot());
        System.out.println();

        // 玩家进行游戏
        System.out.println("--- 开始冒险 ---");
        player.fight();
        player.fight();
        player.levelUp();
        player.moveTo("森林");
        player.showStatus();
        System.out.println();

        // 场景2：存档2 - 森林状态
        System.out.println("--- 场景2：在森林保存进度 ---");
        saveSystem.saveToSlot("存档2-森林", player.snapshot());
        System.out.println();

        // 继续冒险
        System.out.println("--- 继续冒险 ---");
        player.moveTo("魔王城");
        player.fight();
        player.showStatus();
        System.out.println();

        // 场景3：挑战BOSS前存档
        System.out.println("--- 场景3：挑战BOSS前保存 ---");
        saveSystem.saveToSlot("存档3-BOSS前", player.snapshot());
        System.out.println();

        // 模拟挑战BOSS失败
        System.out.println("--- 挑战魔王 ---");
        player.fight();
        player.fight();
        player.fight();
        System.out.println("💀 战败了！血量耗尽");
        player.showStatus();
        System.out.println();

        // 读档重来
        System.out.println("--- 读取BOSS前存档 ---");
        GameSnapshot bossSnapshot = saveSystem.loadFromSlot("存档3-BOSS前");
        if (bossSnapshot != null) {
            player.restore(bossSnapshot);
            player.showStatus();
        }
        System.out.println();

        // 查看所有存档
        System.out.println("--- 查看所有存档 ---");
        saveSystem.listSaves();
        System.out.println();

        // 回到新手村
        System.out.println("--- 想念新手村，读取存档1 ---");
        GameSnapshot villageSnapshot = saveSystem.loadFromSlot("存档1-新手村");
        if (villageSnapshot != null) {
            player.restore(villageSnapshot);
            player.showStatus();
        }
        System.out.println();

        // 删除存档
        System.out.println("--- 删除森林存档 ---");
        saveSystem.deleteSlot("存档2-森林");
        saveSystem.listSaves();
    }
}
