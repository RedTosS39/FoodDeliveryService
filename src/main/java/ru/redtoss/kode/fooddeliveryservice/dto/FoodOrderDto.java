package ru.redtoss.kode.fooddeliveryservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;

import java.util.List;

@Setter
@Getter
public class FoodOrderDto {

    private List<FoodDishDto> foodDishDtoList;

    private OrderStatus status;
    @Getter
    private int sum;

    public void setFoodDishDTOList(List<FoodDishDto> foodDishDtoList) {
        this.foodDishDtoList = foodDishDtoList;
        this.setSum(foodDishDtoList);
    }

    public void setSum(List<FoodDishDto> list) {
        this.sum = list.stream()
                .mapToInt(FoodDishDto::getSum)
                .sum();
    }

    public List<FoodDishDto> getFoodDishDTOList() {
        return foodDishDtoList;
    }

    @Override
    public String toString() {
        return "FoodOrderDTO{" +
               "foodDishDTOList=" + foodDishDtoList +
               ", status=" + status +
               ", sum=" + sum +
               '}';
    }
}
