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
public class PersonProfileEntity {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private PersonEntity personEntity;

    @Column(name = "NAME")
    @NotNull(message = "Enter the username")
    @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов")
    private String name;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "ID")
    private CourierEntity courierEntity;


    @Column(name = "UPDATED_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedDate;

    @Setter
    @Column(name = "status")
    private Status status;

    @Setter
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne(mappedBy = "personProfileEntity", cascade = CascadeType.ALL)
    private CartEntity cartEntity;

    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodOrderEntity> foodOrderEntityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
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

    public CourierEntity getCourierEntity() {
        return courierEntity;
    }

    public void setCourierEntity(CourierEntity courierEntity) {
        this.courierEntity = courierEntity;
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

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public List<FoodOrderEntity> getFoodOrderEntityList() {
        return foodOrderEntityList;
    }

    public void setFoodOrderEntityList(List<FoodOrderEntity> foodOrderEntityList) {
        this.foodOrderEntityList = foodOrderEntityList;
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
