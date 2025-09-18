package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileDTO;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileUpdater;
import ru.redtoss.kode.fooddeliveryservice.entities.Courier;
import ru.redtoss.kode.fooddeliveryservice.entities.Person;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.Status;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PeopleService implements ConvertEntity {
    private final PersonRepository personRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository, ProfileRepository profileRepository) {
        this.personRepository = personRepository;
        this.profileRepository = profileRepository;
    }

    public List<ProfileDTO> findAllPeople(Role role) {

        return profileRepository.findAll()
                .stream()
                .filter(it -> it.getRole() == role
                              && it.getIsActive())
                .map(this::converToProfileDTO)
                .collect(Collectors.toList());
    }

    public ProfileDTO findProfileById(int id) {
        Optional<PersonProfile> optionalPersonProfile = profileRepository.findById(id);
        if (optionalPersonProfile.isPresent()) {
            return converToProfileDTO(optionalPersonProfile.get());
        }
        throw new PersonNotFoundException();
    }

    @Transactional
    public void createPerson(ProfileUpdater personDTO, Role role) {
        Person person = convertToPerson(personDTO);
        PersonProfile profile = createUserProfile(person.getName(), role);
        profile.setPerson(person);
        profile.setStatus(null);
        profile.setIsActive(true);
        profileRepository.save(profile);
        personRepository.save(person);
    }

    @Transactional
    public void createCourier(ProfileUpdater courierDTO, Role role) {
        Courier courier = converToCourier(courierDTO);
        PersonProfile profile = createUserProfile(courier.getName(), role);
        profile.setIsActive(true);
        profile.setCourier(courier);
        profile.setStatus(Status.ONLINE);
        profileRepository.save(profile);
    }

    @Transactional
    public void update(int id, ProfileUpdater updater) {
        Optional<PersonProfile> profile = personRepository.findProfileWithId(id);
        if (profile.isPresent()) {
            PersonProfile personProfile = profile.get();
            personProfile.setId(id);
            personProfile.setName(updater.getName());
            personProfile.setUpdatedDate(LocalDateTime.now());
            profileRepository.save(personProfile);
        }

        System.out.println("Person updated:");
    }

    @Transactional
    public void deletePersonProfile(int id) {
        Optional<PersonProfile> profile = profileRepository.findById(id);
        if (profile.isPresent())
            profile.get().setIsActive(false);
        else
            throw new PersonNotFoundException();
    }

    private PersonProfile createUserProfile(String name, Role role) {
        PersonProfile profile = new PersonProfile();
        profile.setName(name);
        profile.setRole(role);
        profile.setUpdatedDate(LocalDateTime.now());
        return profile;
    }
}
