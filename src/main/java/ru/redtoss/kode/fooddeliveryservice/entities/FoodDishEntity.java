package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Food_Dish")
@NoArgsConstructor
public class FoodDishEntity {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DISH_NAME")
    private String dishName;

    @Column(name = "price")
    private Integer price;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FOOD_CART_ID", referencedColumnName = "ID")
    private CartEntity cartEntity;

    @Column(name = "QUANTITY")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID", referencedColumnName = "ID")
    private FoodMenuEntity foodMenuEntity;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FOOD_ORDER_ID", referencedColumnName = "ID")
    private FoodOrderEntity foodOrder;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public FoodOrderEntity getFoodOrderEntity() {
        return foodOrder;
    }

    public void setFoodOrderEntity(FoodOrderEntity foodOrderEntity) {
        this.foodOrder = foodOrderEntity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public FoodMenuEntity getFoodMenuEntity() {
        return foodMenuEntity;
    }

    public void setFoodMenuEntity(FoodMenuEntity foodMenuEntity) {
        this.foodMenuEntity = foodMenuEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "FoodDish{" +
               "id=" + id +
               ", dishName='" + dishName + '\'' +
               ", price=" + price +
               '}';
    }
}

