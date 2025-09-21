package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.redtoss.kode.fooddeliveryservice.models.RestaurantType;

@Entity
@Table(name = "Restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "RESTAURANT_TYPE")
    private RestaurantType restaurantType;


    @Column(name = "RATING")
    @Min(value = 0)
    @Max(value = 5)
    private Float rating;


    @Min(value = 0)
    @Max(value = 5)
    public Float getRating() {
        return rating;
    }

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToOne(mappedBy = "restaurantEntity")
    private FoodMenuEntity menu;

    @OneToOne(mappedBy = "restaurantEntity")
    private CartEntity cartEntity;



    public RestaurantEntity() {
    }

    public CartEntity getCart() {
        return cartEntity;
    }

    public void setCart(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public FoodMenuEntity getMenu() {
        return menu;
    }

    public void setMenu(FoodMenuEntity menu) {
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, max = 50) String name) {
        this.name = name;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", restaurantType=" + restaurantType +
               ", rating=" + rating +
               ", isActive=" + isActive +
               '}';
    }
}



