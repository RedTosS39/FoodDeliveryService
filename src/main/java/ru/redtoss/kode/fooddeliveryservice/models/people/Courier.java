package ru.redtoss.kode.fooddeliveryservice.models.people;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.redtoss.kode.fooddeliveryservice.models.Status;

@Entity
public class Courier {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, max = 10, message = "Имя должно быть от 2 до 10 символов")
    private String name;

    @Column(name = "status")
    private Status status;

    @OneToOne(mappedBy = "courier")
    private PersonProfile personProfile;


    public Courier() {
    }

    public Courier(int id, String name, Status status, PersonProfile personProfile) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.personProfile = personProfile;
    }

    public PersonProfile getPersonProfile() {
        return personProfile;
    }

    public void setPersonProfile(PersonProfile personProfile) {
        if (personProfile != null) {
            personProfile.setCourier(this);
        }
        this.personProfile = personProfile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
