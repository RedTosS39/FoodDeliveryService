package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.redtoss.kode.fooddeliveryservice.entities.RestaurantEntity;


@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
}
