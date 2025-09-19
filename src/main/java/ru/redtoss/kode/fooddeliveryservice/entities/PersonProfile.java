package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.Status;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@RequiredArgsConstructor
@Table(name = "PERSON_PROFILE")
public class PersonProfile{

    @Id
    @Setter
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
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne(mappedBy = "personProfile", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodOrder> foodOrderList;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public @NotNull(message = "Enter the username") @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Enter the username") @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов") String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<FoodOrder> getFoodOrderList() {
        return foodOrderList;
    }

    public void setFoodOrderList(List<FoodOrder> foodOrderList) {
        this.foodOrderList = foodOrderList;
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
