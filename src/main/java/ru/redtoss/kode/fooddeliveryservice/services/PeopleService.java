package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.people.Courier;
import ru.redtoss.kode.fooddeliveryservice.models.people.Person;
import ru.redtoss.kode.fooddeliveryservice.models.people.PersonProfile;
import ru.redtoss.kode.fooddeliveryservice.repositories.CourierRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)

public class PeopleService {
    private final PersonRepository personRepository;
    private final ProfileRepository profileRepository;
    private final CourierRepository courierRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository, ProfileRepository profileRepository, CourierRepository courierRepository) {
        this.personRepository = personRepository;
        this.profileRepository = profileRepository;
        this.courierRepository = courierRepository;
    }

    public List<PersonProfile> findAllPeople(Role role) {
        return profileRepository.findAll().stream().filter(it -> it.getRole() == role).collect(Collectors.toCollection(ArrayList::new));
    }



    public Optional<PersonProfile> findProfileById(int id) {
        return profileRepository.findById(id);
    }

    @Transactional
    public void createPerson(Person person) {
        PersonProfile profile = createUserProfile(person.getUserName(), Role.BUYER);
        profile.setPerson(person);
        profileRepository.save(profile);
        personRepository.save(person);
    }


    @Transactional
    public void createCourier(Courier courier) {
        PersonProfile profile = createUserProfile(courier.getName(), Role.COURIER);
        profile.setCourier(courier);
        profileRepository.save(profile);
        courierRepository.save(courier);
    }

    @Transactional
    public void updatePersonProfile(int id, PersonProfile updatedPerson) {
        Optional<PersonProfile> profile = personRepository.findProfileWithId(id);
        if (profile.isPresent()) {
            PersonProfile personProfile = profile.get();
            personProfile.setId(id);
            personProfile.setName(updatedPerson.getName());
            personProfile.setUpdatedDate(LocalDate.now());
            profileRepository.save(personProfile);
        }
        System.out.println("Person updated");
    }

    @Transactional
    public void deletePersonProfile(int id) {
        profileRepository.deleteById(id);
        personRepository.deleteById(id);

        System.out.println("user deleted");
    }


    private PersonProfile createUserProfile(String name, Role role) {
        PersonProfile profile = new PersonProfile();
        profile.setName(name);
        profile.setRole(role);
        profile.setUpdatedDate(LocalDate.now());
        return profile;
    }

    public void showTest() {
        List<PersonProfile> profiles = profileRepository.findAll();
        for (PersonProfile profile : profiles) {
            System.out.println(profile);
        }
    }

}
