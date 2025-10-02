package ru.redtoss.kode.fooddeliveryservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "Payment")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @Column(name = "id")
    private int id;
    private Boolean status;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "id")
    private FoodOrderEntity foodOrder;
}
 