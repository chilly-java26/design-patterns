package com.designpatterns.decorator.practice;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // 简单衣服
        Clothes clothes = new SimpleClothes();
        System.out.println(clothes.getDecorations());

        // 带纽扣的衣服
        Clothes clothesWithButton = new ButtonDecoration(new SimpleClothes());
        System.out.println(clothesWithButton.getDecorations());

        // 带2颗纽扣的衣服
        Clothes clothesWithTwoButtons = new ButtonDecoration(new ButtonDecoration(new SimpleClothes()));
        System.out.println(clothesWithTwoButtons.getDecorations());

        // File IO的装饰器例子（加缓冲）
        FileInputStream fis = new FileInputStream("test.txt");
        // 先读到 8KB 缓冲区
        BufferedInputStream bis = new BufferedInputStream(fis);
        // 处理字节
        int data;
        while ((data = bis.read()) != -1) {
            System.out.println((char) data);
        }
        System.out.println("提供了缓冲功能，提高读取效率");
        bis.close();
    }
}
