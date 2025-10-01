package ru.redtoss.kode.fooddeliveryservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonEntity;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @MockitoBean
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @Test
    void findAll() {
        PersonEntity person = new PersonEntity(1, "Test");
        PersonEntity person2 = new PersonEntity(2, "Test2");
        List<PersonEntity> expected = Arrays.asList(person, person2);

        when(personRepository.findAll()).thenReturn(expected);

        List<PersonEntity> actual = personService.findAll();

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
        verify(personRepository, times(1)).findAll();
    }
}