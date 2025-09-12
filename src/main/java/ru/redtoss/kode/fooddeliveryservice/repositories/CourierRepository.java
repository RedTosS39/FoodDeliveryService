package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.people.Courier;
import ru.redtoss.kode.fooddeliveryservice.models.people.PersonProfile;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Integer> {

}
