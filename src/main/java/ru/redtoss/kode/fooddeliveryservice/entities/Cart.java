package ru.redtoss.kode.fooddeliveryservice.entities;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor

@Table(name = "Food_Cart")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_PROFILE_ID", referencedColumnName = "ID")
    private PersonProfile personProfile;

    @OneToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FoodDish> foodDishes;

    @Transient
    private int sum;

    public int countSum() {
        sum = 0;
        for (FoodDish foodDish : foodDishes) {
            sum += foodDish.getQuantity() * foodDish.getPrice();
        }

        return sum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<FoodDish> getFoodDishes() {
        return foodDishes;
    }

    public void setFoodDishes(List<FoodDish> foodDishes) {
        this.foodDishes = foodDishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getId() {
        return id;
    }

    public PersonProfile getPersonProfile() {
        return personProfile;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPersonProfile(PersonProfile personProfile) {
        this.personProfile = personProfile;
    }


    @Override
    public String toString() {
        return "Cart{" +
               "id=" + id +
               ", personProfile=" + personProfile +
               '}';
    }
}
