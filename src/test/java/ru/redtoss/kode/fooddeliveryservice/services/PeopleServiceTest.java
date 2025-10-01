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
import ru.redtoss.kode.fooddeliveryservice.dto.PersonDto;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileDto;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfileEntity;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.repositories.CartRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    private static final String NAME = "test";
    private static final int PROFILE_ID = 1;
    private static final LocalDateTime DATE_TIME = LocalDateTime.now();


    private final ConvertEntity convertEntity = new ConvertEntity() {
        @Override
        public PersonProfileEntity convertToPersonProfile(CourierDto courierDTO) {
            return ConvertEntity.super.convertToPersonProfile(courierDTO);
        }

        @Override
        public ProfileDto converToProfileDTO(PersonProfileEntity person) {
            return ConvertEntity.super.converToProfileDTO(person);
        }
    };

    @MockitoBean
    private ProfileRepository profileRepository;
    @MockitoBean
    private CartRepository cartRepository;
    @Autowired
    private PeopleService peopleService;


    @Test
    void findAllPeople() {
        PersonProfileEntity mockProfile = cratePersonProfile();
        mockProfile.setRole(Role.BUYER);
        List<PersonProfileEntity> expected = Arrays.asList(mockProfile);
        when(profileRepository.findAll()).thenReturn(expected);
        List<ProfileDto> actual = peopleService.findAllPeople(Role.BUYER);
        assertEquals(actual.size(), expected.size());
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void findProfileById() {
        PersonProfileEntity mockProfile = cratePersonProfile();
        mockProfile.setRole(Role.BUYER);
        ProfileDto expected = convertEntity.converToProfileDTO(mockProfile);
        when(profileRepository.findById(PROFILE_ID)).thenReturn(Optional.of(mockProfile));
        ProfileDto actual = peopleService.findProfileById(PROFILE_ID);
        assertEquals(expected, actual);
        verify(profileRepository, times(1)).findById(PROFILE_ID);
    }

    @Test
    @Transactional
    void createPerson() {
        ProfileUpdater profileUpdater = mock(ProfileUpdater.class);
        when(profileUpdater.getName()).thenReturn(NAME);

        PersonProfileEntity expected = cratePersonProfile();
        expected.setRole(Role.BUYER);
        when(profileRepository.save(any(PersonProfileEntity.class))).thenReturn(expected);
        peopleService.createPerson(profileUpdater, expected.getRole());

        verify(profileRepository, times(1)).save(any(PersonProfileEntity.class));
    }

    @Test
    @Transactional
    void createCourier() {
        ProfileUpdater profileUpdater = mock(ProfileUpdater.class);
        when(profileUpdater.getName()).thenReturn(NAME);
        PersonProfileEntity expected = cratePersonProfile();
        expected.setRole(Role.COURIER);

        when(profileRepository.save(any(PersonProfileEntity.class))).thenReturn(expected);
        peopleService.createCourier(profileUpdater, expected.getRole());

        verify(profileRepository, times(1)).save(any(PersonProfileEntity.class));
    }

    @Test
    void update() {

    }

    @Test
    void deletePersonProfile() {
    }

    private PersonProfileEntity cratePersonProfile() {
        PersonProfileEntity personProfileEntity = new PersonProfileEntity();
        personProfileEntity.setId(PROFILE_ID);
        personProfileEntity.setName(NAME);
        personProfileEntity.setUpdatedDate(DATE_TIME);
        personProfileEntity.setIsActive(true);
        return personProfileEntity;
    }
}