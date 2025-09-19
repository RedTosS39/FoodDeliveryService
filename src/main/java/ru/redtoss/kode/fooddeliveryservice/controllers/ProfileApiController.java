package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.PersonDTO;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileDTO;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.services.ConvertEntity;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;
import ru.redtoss.kode.fooddeliveryservice.utils.DefaultErrorResponse;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;

import java.util.List;


@RestController
@RequestMapping("/users")
@ResponseBody
public class ProfileApiController implements ShowErrorMessage, ConvertEntity {

    private final PeopleService peopleService;

    @Autowired
    public ProfileApiController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProfileDTO> findAll() {
        return peopleService.findAllPeople(Role.BUYER);
    }

    @GetMapping("/{id}")
    public ProfileDTO getPerson(@PathVariable int id) {
        return peopleService.findProfileById(id);
    }

    @GetMapping("/")
    public List<ProfileDTO> getPersonByRole(@RequestParam Role role) {
        return peopleService.findAllPeople(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable int id, @RequestBody @Valid PersonDTO profileDTO) {
        peopleService.update(id, profileDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = showErrorMessage(bindingResult);
            throw new PersonNotCreatedException(errorMsg);
        }
        peopleService.createPerson(personDTO, Role.BUYER);
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> setActiveProfile(@PathVariable int id) {
        peopleService.deletePersonProfile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<DefaultErrorResponse> handleException(PersonNotFoundException error) {
        DefaultErrorResponse response = new DefaultErrorResponse(
                "User with id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
