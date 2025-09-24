package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodMenuEntity;

public interface FoodMenuRepository extends JpaRepository<FoodMenuEntity, Integer> {
}
