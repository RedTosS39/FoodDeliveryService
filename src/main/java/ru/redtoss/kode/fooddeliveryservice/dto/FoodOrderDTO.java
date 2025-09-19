package ru.redtoss.kode.fooddeliveryservice.dto;

import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;

import java.util.List;

public class FoodOrderDTO {

    private List<FoodDishDTO> foodDishDTOList;
    private OrderStatus status;
    private int sum;

    public void setFoodDishDTOList(List<FoodDishDTO> foodDishDTOList) {
        this.foodDishDTOList = foodDishDTOList;
        this.setSum(foodDishDTOList);
    }

    public void setSum(List<FoodDishDTO> list) {
        this.sum = list.stream()
                .mapToInt(FoodDishDTO::getSum)
                .sum();
    }

    public List<FoodDishDTO> getFoodDishDTOList() {
        return foodDishDTOList;
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
               "foodDishDTOList=" + foodDishDTOList +
               ", status=" + status +
               ", sum=" + sum +
               '}';
    }
}
