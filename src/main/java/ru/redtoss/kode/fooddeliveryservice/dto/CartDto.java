package ru.redtoss.kode.fooddeliveryservice.dto;

import java.util.List;

public class CartDto {
    private final List<FoodDishDto> items;
    private final int sum;


    public CartDto(List<FoodDishDto> items) {
        this.items = items;
        this.sum = items.stream().mapToInt(FoodDishDto::getSum).sum();
    }


    public List<FoodDishDto> getItems() {
        return items;
    }

    public int getSum() {
        return sum;
    }

}
