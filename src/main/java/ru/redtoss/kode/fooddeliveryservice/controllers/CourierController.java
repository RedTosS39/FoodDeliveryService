package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.people.Courier;
import ru.redtoss.kode.fooddeliveryservice.services.PeopleService;

@Controller
@RequestMapping("/couriers")
public class CourierController {

    private final PeopleService peopleService;

    @Autowired
    public CourierController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GetMapping
    public String showCouriers(Model model) {
        model.addAttribute("couriers", peopleService.findAllPeople(Role.COURIER));

        return "people/couriers";
    }

    @GetMapping("/add-courier")
    public String addCourierForm(@ModelAttribute("courier") Courier courier, Model model) {
        model.addAttribute("courier", courier);

        return "people/add-courier";
    }

    @PostMapping
    public String addCourier(@ModelAttribute("courier") @Valid Courier courier,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/add-courier";
        }
        peopleService.createCourier(courier);
        return "redirect:/couriers";
    }
}
