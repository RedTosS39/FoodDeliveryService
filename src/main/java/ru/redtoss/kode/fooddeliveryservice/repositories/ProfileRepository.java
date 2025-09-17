package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;

public interface ProfileRepository extends JpaRepository<PersonProfile, Integer> {
}
