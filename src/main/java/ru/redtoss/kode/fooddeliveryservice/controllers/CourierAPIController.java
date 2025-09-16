package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.CourierDTO;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;
import ru.redtoss.kode.fooddeliveryservice.utils.ErrorResponse;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/couriers")
public class CourierAPIController implements ShowErrorMessage, ConvertEntity {

    private final PeopleService peopleService;

    @Autowired
    public CourierAPIController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CourierDTO> findAll() {
        return peopleService.findAllPeople(Role.COURIER).stream().map(this::convertToCourierDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourierDTO getCourierDTO(@PathVariable int id) {
        return convertToCourierDTO(peopleService.findProfileById(id).orElseThrow(PersonNotFoundException::new));
    }

    @GetMapping("/")
    public List<CourierDTO> getPersonByRole(@RequestParam Role role) {
        return peopleService.findAllPeople(role).stream().map(this::convertToCourierDTO).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCourier(@RequestBody @Valid CourierDTO courierDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = showErrorMessage(bindingResult);
            throw new PersonNotCreatedException(message);
        }
        peopleService.createCourier(converToCourier(courierDTO));
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void updateProfile(@PathVariable int id, @RequestBody @Valid CourierDTO courierDTO) {
        peopleService.updatePersonProfile(id, convertToPersonProfile(courierDTO));
    }

    @DeleteMapping("/{id}")
    public void setActiveProfile(@PathVariable int id) {
        peopleService.deletePersonProfile(id);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PersonNotFoundException error) {
        ErrorResponse response = new ErrorResponse(
                "User with id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}

