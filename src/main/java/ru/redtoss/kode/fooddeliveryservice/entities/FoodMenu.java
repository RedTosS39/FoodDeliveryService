package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "Food_Menu")
public class FoodMenu {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "foodMenu")
    private List<FoodDish> dishes;

    public List<FoodDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodDish> dishes) {
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "FoodMenu{" +
               "dishes=" + dishes.stream().toList() +
               '}';
    }
}
