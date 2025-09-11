package ru.redtoss.kode.fooddeliveryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redtoss.kode.fooddeliveryservice.models.people.PersonProfile;

public interface ProfileRepository extends JpaRepository<PersonProfile, Integer> {


}
