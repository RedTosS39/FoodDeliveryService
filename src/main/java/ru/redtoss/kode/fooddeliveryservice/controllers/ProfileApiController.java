package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.ProfileDto;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.services.ConvertEntity;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;
import ru.redtoss.kode.fooddeliveryservice.services.ShowErrorMessage;

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
    public List<ProfileDto> findAll() {
        return peopleService.findAllPeople(Role.BUYER);
    }

    @GetMapping("/{id}")
    public ProfileDto getPerson(@PathVariable int id) {
        return peopleService.findProfileById(id);
    }

    @GetMapping("/")
    public List<ProfileDto> getPersonByRole(@RequestParam Role role) {
        return peopleService.findAllPeople(role);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> setActiveProfile(@PathVariable int id) {
        peopleService.deletePersonProfile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
