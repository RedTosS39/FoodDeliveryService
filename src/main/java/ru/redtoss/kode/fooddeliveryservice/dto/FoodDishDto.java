package ru.redtoss.kode.fooddeliveryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FoodDishDto {
    @Getter
    @Size(min = 2, max = 50)
    private String dishName;

    @Getter
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

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }
}
