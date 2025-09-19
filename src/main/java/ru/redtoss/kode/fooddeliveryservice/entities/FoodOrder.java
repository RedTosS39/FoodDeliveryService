package ru.redtoss.kode.fooddeliveryservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Food_Order")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private PersonProfile people;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "ID")
    private Courier courier;


    @OneToMany(mappedBy = "foodOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodDish> foodDishes;

    @Column(name = "STATUS")
    private OrderStatus orderStatus;

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

    public PersonProfile getPeople() {
        return people;
    }

    public void setPeople(PersonProfile people) {
        this.people = people;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public List<FoodDish> getFoodDishes() {
        return foodDishes;
    }

    public void setFoodDishes(List<FoodDish> foodDishes) {
        this.foodDishes = foodDishes;
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
               "id=" + id +
               ", people=" + people +
               ", courier=" + courier +
               ", foodDishes=" + foodDishes +
               ", orderStatus=" + orderStatus +
               '}';
    }
}
