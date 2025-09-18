package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;

@Repository
public interface ProfileRepository extends JpaRepository<PersonProfile, Integer> {
}
