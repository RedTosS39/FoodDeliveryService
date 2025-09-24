package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfileEntity;

import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    @Query("SELECT p FROM  PersonProfileEntity p LEFT JOIN FETCH p.personEntity WHERE p.id=:userId")
    Optional<PersonProfileEntity> findProfileWithId(@Param("userId") int userId);
}
