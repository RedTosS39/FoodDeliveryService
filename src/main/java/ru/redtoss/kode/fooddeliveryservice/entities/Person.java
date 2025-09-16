package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Table(name = "Person")
@RequiredArgsConstructor
public class Person {
    @Id
    @Setter
    @Column(name = "USER_ID")

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Column(name = "USERNAME")
    @NotNull(message = "Enter the username")
    @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов")
    private String name;

    @OneToOne(mappedBy = "person")
    private PersonProfile profile;

    @Setter
    @OneToMany(mappedBy = "people")
    private List<FoodOrder> foodOrder;


    public void setProfile(PersonProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setIsActive(true);
            profile.setPerson(this);
        }
    }


    public void removeProfile(PersonProfile profile) {
        if (profile != null) {
            profile.setPerson(null);
        }
        this.profile = null;
    }


    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", userName='" + name + '}';
    }
}
