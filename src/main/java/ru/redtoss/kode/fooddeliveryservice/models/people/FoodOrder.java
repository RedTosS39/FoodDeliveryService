package ru.redtoss.kode.fooddeliveryservice.models.people;

import jakarta.persistence.*;

@Entity
@Table(name = "FoodOrder")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

}
