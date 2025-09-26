package ru.redtoss.kode.fooddeliveryservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.redtoss.kode.fooddeliveryservice.models.RestaurantType;


@Getter
@Setter
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

    public Boolean getActive() {
        return isActive;
    }

    public @Min(value = 0) @Max(value = 5) Float getRating() {
        return rating;
    }

    public void setRating(@Min(value = 0) @Max(value = 5) Float rating) {
        this.rating = rating;
    }
}
