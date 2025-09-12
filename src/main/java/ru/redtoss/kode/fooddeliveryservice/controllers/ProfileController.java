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

@Controller
@RequestMapping(value = "/users", produces = "text/html;charset=UTF-8")
public class ProfileController {

    private final PeopleService peopleService;

    @Autowired
    public ProfileController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/add-user")
    public String createUserForm(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("person", person);
        return "people/add-user";
    }

    @PostMapping
    public String createUser(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/add-user";
        }

        peopleService.createPerson(person);
        return "redirect:/users";
    }

    @GetMapping(value = "/{id}")
    public String getProfile(@PathVariable("id") int id, Model model) {
        model.addAttribute("profile", peopleService.findProfileById(id));

        return "people/profile";
    }


    @GetMapping("{id}/edit-profile")
    public String updateProfileForm(@PathVariable("id") int id, Model model) {
        PersonProfile profile = peopleService.findProfileById(id).get();
        model.addAttribute("profile", profile);
        return "people/edit-profile";
    }


    @PostMapping("{id}")
    public String updateProfile(@PathVariable("id") int id,
                                @ModelAttribute("profile") @Valid PersonProfile profile,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/edit-profile";
        }
        peopleService.updatePersonProfile(id, profile);
        return "redirect:/users/" + profile.getId();
    }


    @DeleteMapping("/{id}")
    public String deleteProfile(@PathVariable("id") int id) {
        peopleService.deletePersonProfile(id);

        return "redirect:/users";
    }
}
