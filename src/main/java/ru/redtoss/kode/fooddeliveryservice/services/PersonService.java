package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonEntity;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository1) {
        this.personRepository = personRepository1;
    }

    public List<PersonEntity> findAll() {
        return personRepository.findAll();
    }
}
