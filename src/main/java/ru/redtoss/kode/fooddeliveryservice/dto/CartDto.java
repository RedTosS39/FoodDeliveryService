package ru.redtoss.kode.fooddeliveryservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CartDto {
    @Getter
    private final List<FoodDishDto> items;
    private final int sum;

    public CartDto(List<FoodDishDto> items) {
        this.items = items;
        this.sum = items.stream().mapToInt(FoodDishDto::getSum).sum();
    }

}
