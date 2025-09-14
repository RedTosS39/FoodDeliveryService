package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.entities.Courier;

public interface CourierRepository extends JpaRepository<Courier, Integer> {

}
