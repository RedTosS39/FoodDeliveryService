package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrder;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<FoodOrder, Integer> {

    @EntityGraph(attributePaths = {"foodDishes"})
    List<FoodOrder> findByPeopleId(Integer peopleId);
}
