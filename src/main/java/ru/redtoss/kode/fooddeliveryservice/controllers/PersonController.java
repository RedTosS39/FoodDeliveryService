package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.PersonDto;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PersonNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;
import ru.redtoss.kode.fooddeliveryservice.services.ShowErrorMessage;

@RestController
@RequestMapping("/users")
@ResponseBody
public class PersonController implements ShowErrorMessage {

    private final PeopleService peopleService;


    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @PostMapping
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDto personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = showErrorMessage(bindingResult);
            throw new PersonNotCreatedException(errorMsg);
        }
        peopleService.createPerson(personDTO, Role.BUYER);
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable int id, @RequestBody @Valid PersonDto profileDTO) {
        peopleService.update(id, profileDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
