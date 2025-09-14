package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Dish")
@Getter
@NoArgsConstructor
public class Dish {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DISH_NAME")
    private String dishName;

    @Column(name = "price")
    private int price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID", referencedColumnName = "ID")
    private Menu menu;
}
