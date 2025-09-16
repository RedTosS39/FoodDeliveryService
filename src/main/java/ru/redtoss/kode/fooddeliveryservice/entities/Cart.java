package ru.redtoss.kode.fooddeliveryservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Food_Cart")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_PROFILE_ID", referencedColumnName = "ID")
    private PersonProfile personProfile;


    @OneToMany(mappedBy = "cart")
    private List<FoodDish> foodDishes;
}
