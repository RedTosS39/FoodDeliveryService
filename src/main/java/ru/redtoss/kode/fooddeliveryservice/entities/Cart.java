package ru.redtoss.kode.fooddeliveryservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_PROFILE_ID", referencedColumnName = "ID")
    private PersonProfile personProfile;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    @JoinColumn(name = "ID", referencedColumnName = "DISH_ID")
    private List<Dish> dish;
}
