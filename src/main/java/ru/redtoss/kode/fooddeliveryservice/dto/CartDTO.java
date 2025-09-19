package ru.redtoss.kode.fooddeliveryservice.dto;

import java.util.List;

public class CartDTO {
    private final List<FoodDishDTO> items;
    private final int sum;


    public CartDTO(List<FoodDishDTO> items) {
        this.items = items;
        this.sum = items.stream().mapToInt(FoodDishDTO::getSum).sum();
    }


    public List<FoodDishDTO> getItems() {
        return items;
    }

    public int getSum() {
        return sum;
    }

}
