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
public class CourierEntity {

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

    @OneToOne(mappedBy = "courierEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonProfileEntity personProfileEntity;

    @Setter
    @OneToMany(mappedBy = "courierEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoodOrderEntity> foodOrderEntities;


    public PersonProfileEntity getProfile() {
        return personProfileEntity;
    }

    @Transient
    @Setter
    private int orderCount;


    public void setProfile(PersonProfileEntity personProfileEntity) {
        if (personProfileEntity != null) {
            personProfileEntity.setCourierEntity(this);
        } else {
            this.personProfileEntity = personProfileEntity;
        }
    }
}
