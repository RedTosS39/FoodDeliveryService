package ru.redtoss.kode.fooddeliveryservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfileEntity;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.repositories.CourierRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.OrderRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CouriersServiceTest {
    private static final String NAME = "test";
    private static final int PROFILE_ID = 1;
    private static final LocalDateTime DATE_TIME = LocalDateTime.now();

    @MockitoBean
    private CourierRepository courierRepository;
    @MockitoBean
    private OrderRepository orderRepository;
    @MockitoBean
    private ProfileRepository profileRepository;
    @Autowired
    private CouriersService couriersService;

    @Test
    void testFindAllOrders() {
      
    }

    @Test
    void update() {
        //TODO
    }

    @Test
    void testAssignOrderToCourier() {
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