package ru.redtoss.kode.fooddeliveryservice.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.CourierDto;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileDto;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfileEntity;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.repositories.CartRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    private static final String NAME = "test";
    private static final int PROFILE_ID = 1;
    private static final LocalDateTime DATE_TIME = LocalDateTime.now();
    private final PersonProfileEntity mockProfile = cratePersonProfile(Role.BUYER);
    private final  ProfileUpdater profileUpdater = mock(ProfileUpdater.class);

    @MockitoBean
    private PersonRepository personRepository;
    @MockitoBean
    private ProfileRepository profileRepository;
    @MockitoBean
    private CartRepository cartRepository;
    @Autowired
    private PeopleService peopleService;

    private final ConvertEntity convertEntity = new ConvertEntity() {
        @Override
        public PersonProfileEntity convertToPersonProfile(CourierDto courierDTO) {
            return ConvertEntity.super.convertToPersonProfile(courierDTO);
        }

        @Override
        public ProfileDto converToProfileDTO(PersonProfileEntity person) {
            return ConvertEntity.super.converToProfileDTO(person);
        }

        @Override
        public PersonEntity convertToPerson(ProfileUpdater personDTO) {
            return ConvertEntity.super.convertToPerson(personDTO);
        }
    };


    @Test
    void testFindAllPeople() {
        List<PersonProfileEntity> expected = Arrays.asList(mockProfile);
        when(profileRepository.findAll()).thenReturn(expected);
        List<ProfileDto> actual = peopleService.findAllPeople(Role.BUYER);
        assertEquals(actual.size(), expected.size());
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void testFindProfileById() {
        ProfileDto expected = convertEntity.converToProfileDTO(mockProfile);
        when(profileRepository.findById(PROFILE_ID)).thenReturn(Optional.of(mockProfile));
        ProfileDto actual = peopleService.findProfileById(PROFILE_ID);
        assertEquals(expected, actual);
        verify(profileRepository, times(1)).findById(PROFILE_ID);
    }

    @Test
    @Transactional
    void createPerson() {
        when(profileUpdater.getName()).thenReturn(NAME);
        when(profileRepository.save(any(PersonProfileEntity.class))).thenReturn(mockProfile);
        peopleService.createPerson(profileUpdater, mockProfile.getRole());

        verify(profileRepository, times(1)).save(any(PersonProfileEntity.class));
    }

    @Test
    @Transactional

    void createCourier() {
        when(profileUpdater.getName()).thenReturn(NAME);
        PersonProfileEntity expected = cratePersonProfile(Role.COURIER);

        when(profileRepository.save(any(PersonProfileEntity.class))).thenReturn(expected);
        peopleService.createCourier(profileUpdater, expected.getRole());

        verify(profileRepository, times(1)).save(any(PersonProfileEntity.class));
    }

    @Test
    @Transactional
    void update() {
        //TODO:org.mockito.exceptions.misusing.MissingMethodInvocationException:
    }

    @Test
    @Transactional
    void deletePersonProfile() {
        when(profileRepository.findById(PROFILE_ID)).thenReturn(Optional.of(mockProfile));
        peopleService.deletePersonProfile(PROFILE_ID);
        verify(profileRepository, times(1)).findById(PROFILE_ID);
    }

    private PersonProfileEntity cratePersonProfile(Role role) {
        PersonProfileEntity personProfileEntity = new PersonProfileEntity();
        personProfileEntity.setId(PROFILE_ID);
        personProfileEntity.setName(NAME);
        personProfileEntity.setUpdatedDate(DATE_TIME);
        personProfileEntity.setIsActive(true);
        personProfileEntity.setRole(role);
        return personProfileEntity;
    }
}