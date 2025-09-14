package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;

public interface ProfileRepository extends JpaRepository<PersonProfile, Integer> {

    @Modifying
    @Query("update PersonProfile  set isActive = false WHERE person.id=:id")
    void setActiveWithId(int id);
}
