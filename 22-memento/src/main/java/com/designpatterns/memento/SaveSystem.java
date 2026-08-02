package com.designpatterns.memento;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理者（Caretaker）：存档系统
 * 负责管理多个存档槽位，但不能修改快照内容
 */
public class SaveSystem {
    private Map<String, GameSnapshot> saveSlots = new HashMap<>();

    // 保存到指定槽位
    public void saveToSlot(String slotName, GameSnapshot snapshot) {
        saveSlots.put(slotName, snapshot);
        System.out.println("💾 已保存到槽位：" + slotName);
    }

    // 从指定槽位读取
    public GameSnapshot loadFromSlot(String slotName) {
        GameSnapshot snapshot = saveSlots.get(slotName);
        if (snapshot == null) {
            System.out.println("❌ 槽位 " + slotName + " 不存在！");
            return null;
        }
        System.out.println("📂 从槽位 " + slotName + " 读取存档");
        return snapshot;
    }

    // 删除存档
    public void deleteSlot(String slotName) {
        if (saveSlots.remove(slotName) != null) {
            System.out.println("🗑️  已删除槽位：" + slotName);
        } else {
            System.out.println("❌ 槽位 " + slotName + " 不存在！");
        }
    }

    // 列出所有存档
    public void listSaves() {
        if (saveSlots.isEmpty()) {
            System.out.println("📁 暂无存档");
            return;
        }
        System.out.println("📁 存档列表：");
        saveSlots.forEach((slot, snapshot) -> {
            System.out.println("  - " + slot + ": " + snapshot);
        });
    }
}
