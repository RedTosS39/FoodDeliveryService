package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodMenu;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Integer> {
}
