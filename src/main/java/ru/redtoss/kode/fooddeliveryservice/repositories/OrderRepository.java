package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrder;

public interface OrderRepository extends JpaRepository<FoodOrder, Integer> {
}
