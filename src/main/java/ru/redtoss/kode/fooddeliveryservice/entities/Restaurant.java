package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @UniqueElements
    private String name;




//    private FoodMenu menu;

    @Column(name = "isActive")
    private Boolean isActive;
}
