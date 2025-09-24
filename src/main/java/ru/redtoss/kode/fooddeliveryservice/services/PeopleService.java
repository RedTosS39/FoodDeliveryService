package ru.redtoss.kode.fooddeliveryservice.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileDto;
import ru.redtoss.kode.fooddeliveryservice.entities.*;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.Status;
import ru.redtoss.kode.fooddeliveryservice.repositories.CartRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PeopleService implements ConvertEntity {
    private final PersonRepository personRepository;
    private final ProfileRepository profileRepository;
    private final CartRepository cartRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository,
                         ProfileRepository profileRepository,
                         CartRepository cartRepository) {
        this.personRepository = personRepository;
        this.profileRepository = profileRepository;
        this.cartRepository = cartRepository;
    }

    public List<ProfileDto> findAllPeople(Role role) {

        return profileRepository.findAll()
                .stream()
                .filter(it -> it.getRole() == role
                              && it.getActive())
                .map(this::converToProfileDTO)
                .collect(Collectors.toList());
    }

    public ProfileDto findProfileById(int id) {
        Optional<PersonProfileEntity> optionalPersonProfile = profileRepository.findById(id);
        if (optionalPersonProfile.isPresent()) {
            return converToProfileDTO(optionalPersonProfile.get());
        }
        throw new PersonNotFoundException();
    }

    @Transactional
    public void createPerson(ProfileUpdater personDTO, Role role) {
        PersonEntity personEntity = convertToPerson(personDTO);
        PersonProfileEntity profile = createUserProfile(personEntity.getName(), role);

        profile.setPersonEntity(personEntity);
        profile.setStatus(null);
        profile.setIsActive(true);

        Hibernate.initialize(CartEntity.class);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setPersonProfileEntity(profile);
        List<FoodDishEntity> dishes = new ArrayList<>();
        cartEntity.setFoodDishEntities(dishes);

        profile.setCartEntity(cartEntity);
        cartRepository.save(cartEntity);

        profileRepository.save(profile);
        personRepository.save(personEntity);
    }

    @Transactional
    public void createCourier(ProfileUpdater courierDTO, Role role) {
        CourierEntity courierEntity = convertToCourierEntity(courierDTO);
        PersonProfileEntity profile = createUserProfile(courierEntity.getName(), role);
        profile.setIsActive(true);
        profile.setCourierEntity(courierEntity);
        profile.setStatus(Status.ONLINE);
        profileRepository.save(profile);
    }

    @Transactional
    public void update(int id, ProfileUpdater updater) {
        Optional<PersonProfileEntity> profile = personRepository.findProfileWithId(id);
        if (profile.isPresent()) {
            PersonProfileEntity personProfileEntity = profile.get();
            personProfileEntity.setId(id);
            personProfileEntity.setName(updater.getName());
            personProfileEntity.setUpdatedDate(LocalDateTime.now());
            profileRepository.save(personProfileEntity);


        }
        log.info("Person updated:");
    }

    @Transactional
    public void deletePersonProfile(int id) {
        Optional<PersonProfileEntity> profile = profileRepository.findById(id);
        if (profile.isPresent())
            profile.get().setIsActive(false);
        else
            throw new PersonNotFoundException();
    }

    private PersonProfileEntity createUserProfile(String name, Role role) {

        PersonProfileEntity profile = new PersonProfileEntity();
        profile.setName(name);
        profile.setRole(role);
        profile.setUpdatedDate(LocalDateTime.now());

        return profile;
    }


}
