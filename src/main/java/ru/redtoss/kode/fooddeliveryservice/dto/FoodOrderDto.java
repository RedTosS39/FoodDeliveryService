package ru.redtoss.kode.fooddeliveryservice.dto;

import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;

import java.util.List;

public class FoodOrderDto {

    private List<FoodDishDto> foodDishDtoList;
    private OrderStatus status;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getSum() {
        return sum;
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
