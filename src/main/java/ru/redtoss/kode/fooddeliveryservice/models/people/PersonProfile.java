package ru.redtoss.kode.fooddeliveryservice.models.people;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String name;

    @Column(name = "ROLE")
    private String role;


    @Column(name = "UPDATED_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updatedDate;


    public PersonProfile() {
    }


    public PersonProfile(int id, Person person, String name, LocalDate updatedDate, String role) {
        this.id = id;
        this.person = person;
        this.name = name;
        this.updatedDate = updatedDate;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "PersonProfile{" +
               ", profile=" + person +
               ", name='" + name + '\'' +
               ", updatedDate=" + updatedDate +
               ", role='" + role + '\'' +
               '}';
    }
}
