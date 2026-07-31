package com.designpatterns.meal;

public class CookDemo {
    public static void main(String[] args) {
        Meal meal = new Meal.Cook()
                .burger("McDonald")
                .drink("Cocoa Cola")
                .friesSize(3)
                .cook();
        System.out.println(meal);
    }
}
