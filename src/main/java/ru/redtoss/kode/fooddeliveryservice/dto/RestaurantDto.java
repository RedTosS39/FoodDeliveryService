package ru.redtoss.kode.fooddeliveryservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.redtoss.kode.fooddeliveryservice.models.RestaurantType;

public class RestaurantDto {

    private int id;

    @NotNull
    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters")
    private String name;

    private Boolean isActive;

    private RestaurantType restaurantType;


    @Min(value = 0)
    @Max(value = 5)
    private Float rating;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    public @Min(value = 0) @Max(value = 5) Float getRating() {
        return rating;
    }

    public void setRating(@Min(value = 0) @Max(value = 5) Float rating) {
        this.rating = rating;
    }
}
