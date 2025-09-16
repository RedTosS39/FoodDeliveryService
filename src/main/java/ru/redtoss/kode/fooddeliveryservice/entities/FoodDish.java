package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Food_Dish")
@Getter
@NoArgsConstructor
public class FoodDish {

    @Id
    @Column(name = "id", insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DISH_NAME")
    private String dishName;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID", referencedColumnName = "ID")
    private Cart cart;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID", referencedColumnName = "ID")
    private FoodMenu foodMenu;


}

