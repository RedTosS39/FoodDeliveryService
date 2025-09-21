package ru.redtoss.kode.fooddeliveryservice.entities;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor

@Table(name = "Food_Cart")
public class CartEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_PROFILE_ID", referencedColumnName = "ID")
    private PersonProfileEntity personProfileEntity;

    @OneToOne(cascade = CascadeType.ALL)
    private RestaurantEntity restaurantEntity;

    @OneToMany(mappedBy = "cartEntity", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FoodDishEntity> foodDishEntities;

    @Transient
    private int sum;

    public int countSum() {
        sum = 0;
        for (FoodDishEntity foodDishEntity : foodDishEntities) {
            sum += foodDishEntity.getQuantity() * foodDishEntity.getPrice();
        }

        return sum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<FoodDishEntity> getFoodDishEntities() {
        return foodDishEntities;
    }

    public void setFoodDishEntities(List<FoodDishEntity> foodDishEntities) {
        this.foodDishEntities = foodDishEntities;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    public int getId() {
        return id;
    }

    public PersonProfileEntity getPersonProfileEntity() {
        return personProfileEntity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPersonProfileEntity(PersonProfileEntity personProfileEntity) {
        this.personProfileEntity = personProfileEntity;
    }


    @Override
    public String toString() {
        return "Cart{" +
               "id=" + id +
               ", personProfile=" + personProfileEntity +
               '}';
    }
}
