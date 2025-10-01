package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.CourierDto;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodOrderDto;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileDto;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PersonNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.services.CouriersService;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;
import ru.redtoss.kode.fooddeliveryservice.services.ShowErrorMessage;

import java.util.List;

@RestController
@RequestMapping("/couriers")
public class CourierAPIController implements ShowErrorMessage {

    private final PeopleService peopleService;
    private final CouriersService couriersService;


    @Autowired
    public CourierAPIController(PeopleService peopleService, CouriersService couriersService) {
        this.peopleService = peopleService;
        this.couriersService = couriersService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProfileDto> findAll() {
        return peopleService.findAllPeople(Role.COURIER);
    }

    @GetMapping("/{id}")
    public ProfileDto getCourierDTO(@PathVariable int id) {
        return peopleService.findProfileById(id);
    }

    @GetMapping("/")
    public List<ProfileDto> getPersonByRole(@RequestParam(value = "role") Role role) {
        return peopleService.findAllPeople(role);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCourier(@RequestBody @Valid CourierDto courierDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = showErrorMessage(bindingResult);
            throw new PersonNotCreatedException(message);
        }
        peopleService.createCourier(courierDTO, Role.COURIER);
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody @Valid CourierDto courierDTO) {
        couriersService.update(id, courierDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        peopleService.deletePersonProfile(id);
    }


    @PatchMapping("/{id}/assign")
    public ResponseEntity<HttpStatus> assignOrderToCourier(@PathVariable int id, @RequestParam int orderId) {
        couriersService.assignOrderToCourier(id, orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/{id}/orders")
    public List<FoodOrderDto> findAllOrders(@PathVariable("id") int id) {
        return couriersService.findAllOrders(id);
    }


}

