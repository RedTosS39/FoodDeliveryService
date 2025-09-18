package ru.redtoss.kode.fooddeliveryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class FoodDishDTO {
    @Size(min = 2, max = 50)
    private String dishName;

    @Min(value = 1)
    private Integer dishPrice;

    public @Size(min = 2, max = 50) String getDishName() {
        return dishName;
    }

    public void setDishName(@Size(min = 2, max = 50) String dishName) {
        this.dishName = dishName;
    }

    @Min(value = 1)
    public Integer getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }
}
