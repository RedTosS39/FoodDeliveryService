package ru.redtoss.kode.fooddeliveryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class FoodDishDTO {
    @Size(min = 2, max = 50)
    private String dishName;

    @Min(value = 1)
    private Integer dishPrice;

    private int dishQuantity;

    private int sum;

    public void setDishQuantity(int dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public int getSum() {
        return dishPrice * dishQuantity;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Integer getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(@Min(value = 1) Integer dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }


}
