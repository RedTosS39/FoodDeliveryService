package ru.redtoss.kode.fooddeliveryservice.dto;

import ru.redtoss.kode.fooddeliveryservice.entities.FoodDish;

import java.util.Map;

public class CartDTO {
    private String dishName;
    private int dishPrice;
    private int dishQuantity;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(int dishQuantity) {
        this.dishQuantity = dishQuantity;
    }
}
