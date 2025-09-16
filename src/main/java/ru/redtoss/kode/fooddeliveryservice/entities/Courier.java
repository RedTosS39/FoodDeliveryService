package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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

    @OneToOne(mappedBy = "courier")
    private PersonProfile personProfile;


    public PersonProfile getProfile() {
        return personProfile;
    }

    @Setter
    @OneToOne(mappedBy = "courier")
    private FoodOrder foodOrder;

    @Transient
    @Setter
    private int orderCount;


    public int orderCount() {
        if (this.orderCount > 3) {
            return this.orderCount;
        }
        this.orderCount++;
        return this.orderCount;
    }

    public void setProfile(PersonProfile personProfile) {
        if (personProfile != null) {
            personProfile.setCourier(this);
        }
        this.personProfile = personProfile;
    }
}
