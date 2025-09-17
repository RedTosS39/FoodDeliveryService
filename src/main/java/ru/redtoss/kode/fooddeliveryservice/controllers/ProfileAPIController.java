package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.PersonDTO;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.ProfileDTO;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.ProfileUpdater;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;
import ru.redtoss.kode.fooddeliveryservice.utils.ErrorResponse;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;

import java.util.List;


@RestController
@RequestMapping("/users")
@ResponseBody
public class ProfileAPIController implements ShowErrorMessage, ConvertEntity {

    private final PeopleService peopleService;

    @Autowired
    public ProfileAPIController(PeopleService peopleService) {
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
    public void update(@PathVariable int id, @RequestBody @Valid PersonDTO profileDTO) {
        peopleService.update(id, profileDTO);
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
