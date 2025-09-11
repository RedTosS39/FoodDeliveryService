package ru.redtoss.kode.fooddeliveryservice.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.models.people.Person;
import ru.redtoss.kode.fooddeliveryservice.models.people.PersonProfile;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)

public class PeopleService {
    private final PersonRepository personRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public PeopleService(PersonRepository personRepository, ProfileRepository profileRepository) {
        this.personRepository = personRepository;
        this.profileRepository = profileRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Optional<PersonProfile> findProfileById(int id) {
        System.out.println("PROFILE: " + profileRepository.findById(id));
        return profileRepository.findById(id);

    }


    @Transactional
    public void createPerson(Person person) {
        PersonProfile profile = new PersonProfile();
        profile.setPerson(person);
        profile.setName(person.getUserName());
        profile.setUpdatedDate(LocalDate.now());
        profileRepository.save(profile);
        personRepository.save(person);
    }


    @Transactional
    public void updatePersonProfile(int id, PersonProfile updatedPerson) {

        Optional<PersonProfile> profile = personRepository.findProfileWithId(id);
        if (profile.isPresent()) {
            PersonProfile personProfile = profile.get();

            Hibernate.initialize(profile.get());
            //меняем данные
            personProfile.setName(updatedPerson.getName());
            personProfile.setRole(updatedPerson.getRole());

            //Записываем время последнего обновления
            personProfile.setUpdatedDate(LocalDate.now());
            profileRepository.save(personProfile);
        }
        System.out.println("Person updated");
    }


    @Transactional
    public void deletePersonProfile(int id) {
        profileRepository.deleteById(id);
    }
}
