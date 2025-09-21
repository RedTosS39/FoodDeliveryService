package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrderEntity;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<FoodOrderEntity, Integer> {

    @EntityGraph(attributePaths = {"foodDishes"})
    List<FoodOrderEntity> findByPeopleId(Integer peopleId);
}
