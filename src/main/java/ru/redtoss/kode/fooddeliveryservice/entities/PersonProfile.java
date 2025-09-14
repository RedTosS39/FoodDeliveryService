package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.Status;

import java.time.LocalDateTime;

@Setter
@Getter

@Entity
@RequiredArgsConstructor
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "ID")
    private Courier courier;


    @Column(name = "UPDATED_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedDate;

    @Setter
    @Column(name = "status")
    private Status status;

    @Setter
    @Column(name = "isActive")
    private Boolean isActive = true;

    @OneToOne(mappedBy = "personProfile")
    private Cart cart;


    @Override
    public String toString() {
        return "PersonProfile{" +
               ", name='" + name + '\'' +
               ", updatedDate=" + updatedDate +
               ", role='" + role + '\'' +
               '}';
    }
}
