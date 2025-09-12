package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;

@Controller
@RequestMapping(value = "/users", produces = "text/html;charset=UTF-8")
public class PersonController {
    private final PeopleService peopleService;

    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getPeople(Model model) {
        model.addAttribute("people", peopleService.findAllPeople(Role.BUYER));
        peopleService.showTest();
        return "people/users";
    }
}
