package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Menu {

    @Id
    private int menuId;

    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "menu_id")
    private List<Dish> dishList;


    @OneToOne
    private Restaurant restaurant;
}
