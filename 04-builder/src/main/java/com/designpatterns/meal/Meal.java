package com.designpatterns.meal;

public class Meal {
    private final String burger;
    private final String drink;
    private final Integer friesSize;

    public static class Cook {
        private String burger;
        private String drink;
        private Integer friesSize;

        public Cook burger(String burger) {
            this.burger = burger;
            return this;
        }

        public Cook drink(String drink) {
            this.drink = drink;
            return this;
        }

        public Cook friesSize(Integer friesSize) {
            this.friesSize = friesSize;
            return this;
        }

        public Meal cook() {
            if (this.burger == null || this.burger.isEmpty()) {
                throw new IllegalArgumentException("Burger cannot be empty");
            }
            return new Meal(this);
        }
    }

    private Meal(Cook cook) {
        this.burger = cook.burger;
        this.drink = cook.drink;
        this.friesSize = cook.friesSize;
    }

    public String getBurger() {
        return burger;
    }

    public String getDrink() {
        return drink;
    }

    public Integer getFriesSize() {
        return friesSize;
    }

    @Override
    public String toString() {
        return "burger: " + burger +
                ", drink: " + drink +
                ", friesSize: " + friesSize;
    }
}