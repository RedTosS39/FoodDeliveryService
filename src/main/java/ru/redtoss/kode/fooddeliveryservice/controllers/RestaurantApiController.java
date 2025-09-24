package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.RestaurantDto;
import ru.redtoss.kode.fooddeliveryservice.models.RestaurantType;
import ru.redtoss.kode.fooddeliveryservice.services.RestaurantsService;
import ru.redtoss.kode.fooddeliveryservice.utils.DefaultErrorResponse;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotFoundException;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/restaurants")
public class RestaurantApiController implements ShowErrorMessage {

    private final RestaurantsService restaurantsService;

    @Autowired
    public RestaurantApiController(RestaurantsService restaurantsService) {
        this.restaurantsService = restaurantsService;
    }

    @GetMapping
    public List<RestaurantDto> findAll() {
        return restaurantsService.findAll();
    }

    @GetMapping("/{id}")
    public RestaurantDto findRestaurantById(@PathVariable int id) {
        return restaurantsService.findById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createRestaurant(@RequestBody @Valid RestaurantDto restaurantDTO,
                                                       @RequestParam(required = false) RestaurantType restaurantType,
                                                       @RequestParam(required = false) Float rating,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = showErrorMessage(bindingResult);
            throw new RestaurantNotCreatedException(errorMsg);
        }

        if (restaurantType != null) {
            restaurantDTO.setRestaurantType(restaurantType);
        }
        if (rating != null) {
            restaurantDTO.setRating(rating);
        }

        restaurantsService.createRestaurant(restaurantDTO);
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid RestaurantDto restaurantDTO) {
        restaurantsService.updateRestaurant(id, restaurantDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable("id") int id) {
        restaurantsService.deleteRestaurant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<DefaultErrorResponse> handleException(RestaurantNotFoundException error) {
        DefaultErrorResponse response = new DefaultErrorResponse(
                error.getMessage() + " Restaurant with id not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
