package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodMenuDTO;
import ru.redtoss.kode.fooddeliveryservice.services.RestaurantsService;

@RestController
@RequestMapping("/restaurants/{id}")
public class MenuApiController  {


    private final RestaurantsService restaurantsService;

    @Autowired
    public MenuApiController(RestaurantsService restaurantsService) {
        this.restaurantsService = restaurantsService;
    }

    @PostMapping
    private ResponseEntity<HttpStatus> createMenu(@PathVariable("id") int id,
                                                  @RequestBody FoodMenuDTO foodMenuDTO) {
        restaurantsService.createMenu(id, foodMenuDTO);
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
    }
}
