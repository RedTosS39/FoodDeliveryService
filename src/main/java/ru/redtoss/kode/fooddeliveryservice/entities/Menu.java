package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Menu")
public class Menu {

    @Id
    @Column(name = "MENU_ID")
    private int menuId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    private List<Dish> dishes;
}
