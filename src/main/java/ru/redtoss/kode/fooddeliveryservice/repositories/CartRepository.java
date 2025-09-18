package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {


}
