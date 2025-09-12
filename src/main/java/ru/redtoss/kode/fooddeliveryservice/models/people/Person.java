package ru.redtoss.kode.fooddeliveryservice.models.people;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @Column(name = "USERNAME")
    @NotNull(message = "Enter the username")
    @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов")
    private String userName;


    @OneToOne(mappedBy = "person")
    private PersonProfile profile;

    public Person() {
    }


    public Person(int id, String userName, PersonProfile profile) {
        this.id = id;
        this.userName = userName;
        this.profile = profile;
    }

    public PersonProfile getProfile() {
        return profile;
    }

    public void setProfile(PersonProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setPerson(this);
        }
    }

    public void removeProfile(PersonProfile profile) {
        if (profile != null) {
            profile.setPerson(null);
        }
        this.profile = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", userName='" + userName + '}';
    }
}
