package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "Food_Order")
public class FoodOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private PersonProfileEntity people;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "ID")
    private CourierEntity courierEntity;


    @OneToMany(mappedBy = "foodOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodDishEntity> foodDishes;

    @Column(name = "STATUS")
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "foodOrder")
    private PaymentEntity payment;

    public List<FoodDishEntity> getFoodDishes() {
        return foodDishes;
    }

    public void setFoodDishes(List<FoodDishEntity> foodDishes) {
        this.foodDishes = foodDishes;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonProfileEntity getPeople() {
        return people;
    }

    public void setPeople(PersonProfileEntity peopleEntity) {
        this.people = peopleEntity;
    }

    public CourierEntity getCourierEntity() {
        return courierEntity;
    }

    public void setCourierEntity(CourierEntity courierEntity) {
        this.courierEntity = courierEntity;
    }

    public List<FoodDishEntity> getFoodDishEntities() {
        return foodDishes;
    }

    public void setFoodDishEntities(List<FoodDishEntity> foodDishEntities) {
        this.foodDishes = foodDishEntities;
    }


    @Override
    public String toString() {
        return "FoodOrderEntity{" +
               "id=" + id +
               ", peopleEntity=" + people +
               ", courierEntity=" + courierEntity +
               ", foodDishesEntity=" + foodDishes +
               ", orderStatusEntity=" + orderStatus +
               '}';
    }
}
