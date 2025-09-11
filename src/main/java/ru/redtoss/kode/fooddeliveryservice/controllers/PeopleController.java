package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.models.people.Person;
import ru.redtoss.kode.fooddeliveryservice.models.people.PersonProfile;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/users", produces = "text/html;charset=UTF-8")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        System.out.println("Show all");
        return "people/users";
    }

    @GetMapping("/add-user")
    public String createUserForm(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("person", person);
        System.out.println("user Form");
        return "people/add-user";
    }

    @PostMapping
    public String createUser(@ModelAttribute("person") @Valid Person person, Model model, BindingResult bindingResult) {
        model.addAttribute("person", person);
        if (bindingResult.hasErrors()) {
            return "people/add-user";
        }

        peopleService.createPerson(person);
        return "redirect:/users";
    }

    @GetMapping(value = "/{id}")
    public String getProfile(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        model.addAttribute("profile", peopleService.findProfileById(id));

        System.out.println("Show person " + peopleService.findById(id));
        System.out.println("Show profile " +  peopleService.findProfileById(id));
        return "people/profile";
    }


    @PutMapping("{id}/edit-profile")
    public String updateProfile(@PathVariable("id") int id, Model model) {
        Optional<PersonProfile> profile = peopleService.findProfileById(id);
        if (profile.isPresent()) {
            PersonProfile personProfile = profile.get();
            model.addAttribute("profile", personProfile);
        } else {
            model.addAttribute("profile", null);
        }
        System.out.println("Update profile");
        return "people/edit-profile";
    }

    @DeleteMapping("/{id}")
    public String deleteProfile(@PathVariable("id") int id) {
        peopleService.deletePersonProfile(id);

        return "redirect:/users";
    }
}
