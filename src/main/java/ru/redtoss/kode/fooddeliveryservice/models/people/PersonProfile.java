package ru.redtoss.kode.fooddeliveryservice.models.people;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import ru.redtoss.kode.fooddeliveryservice.models.Role;

import java.time.LocalDate;

@Entity
@Table(name = "PERSONPROFILE")
public class PersonProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private Person person;

    @Column(name = "NAME")
    @NotNull(message = "Enter the username")
    @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов")
    private String name;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "ID")
    private Courier courier;



    @Column(name = "UPDATED_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updatedDate;


    public PersonProfile() {
    }


    public PersonProfile(int id, Person person, String name, Role role, Courier courier, LocalDate updatedDate) {
        this.id = id;
        this.person = person;
        this.name = name;
        this.role = role;
        this.courier = courier;
        this.updatedDate = updatedDate;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }



    @Override
    public String toString() {
        return "PersonProfile{" +
               ", name='" + name + '\'' +
               ", updatedDate=" + updatedDate +
               ", role='" + role + '\'' +
               '}';
    }
}
