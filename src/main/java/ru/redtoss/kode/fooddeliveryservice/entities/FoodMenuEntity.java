package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "Food_Menu")
public class FoodMenuEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    private RestaurantEntity restaurantEntity;

    @OneToMany(mappedBy = "foodMenuEntity")
    private List<FoodDishEntity> dishes;

    public List<FoodDishEntity> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodDishEntity> dishes) {
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    @Override
    public String toString() {
        return "FoodMenu{" +
               "dishes=" + dishes.stream().toList() +
               '}';
    }
}
