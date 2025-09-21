package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDishEntity;

public interface FoodDishRepository extends JpaRepository<FoodDishEntity, Integer> {
}
