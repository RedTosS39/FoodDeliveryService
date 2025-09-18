package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.redtoss.kode.fooddeliveryservice.entities.Person;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;

import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p FROM  PersonProfile p LEFT JOIN FETCH p.person WHERE p.id=:userId")
    Optional<PersonProfile> findProfileWithId(@Param("userId") int userId);
}
