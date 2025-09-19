package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "COURIER")
public class Courier {

    @Setter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 10, message = "Имя должно быть от 2 до 10 символов")
    private String name;

    @OneToOne(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonProfile personProfile;

    @Setter
    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoodOrder> foodOrders;


    public PersonProfile getProfile() {
        return personProfile;
    }

    @Transient
    @Setter
    private int orderCount;


    public void setProfile(PersonProfile personProfile) {
        if (personProfile != null) {
            personProfile.setCourier(this);
        } else {
            this.personProfile = personProfile;
        }
    }
}
