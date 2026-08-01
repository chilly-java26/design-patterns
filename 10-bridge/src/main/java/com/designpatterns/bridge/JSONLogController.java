package com.designpatterns.bridge;

public class JSONLogController extends LogController {
    public JSONLogController(Storage storage) {
        super(storage);
    }

    @Override
    public void process(String log){
        // TODO: 1. 将log格式化成JSON格式
        // TODO: 2. 调用storage.save()保存格式化后的数据
    }
}