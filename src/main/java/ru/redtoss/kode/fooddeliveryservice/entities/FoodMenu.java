package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Food_Menu")
public class FoodMenu {

    @Id
    @Column(name = "id")
    private int id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
//    private Restaurant restaurant;
//
//    @OneToMany(mappedBy = "menu")
//    private List<Dish> dishes;
}
