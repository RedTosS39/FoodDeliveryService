package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.CartDTO;
import ru.redtoss.kode.fooddeliveryservice.services.CartService;
import ru.redtoss.kode.fooddeliveryservice.utils.DefaultErrorResponse;
import ru.redtoss.kode.fooddeliveryservice.utils.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotFoundException;

@RestController
@RequestMapping("/cart")
@ResponseBody
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping
    public CartDTO findAll(@RequestParam("personId") Integer personId) {
        return cartService.findAll(personId);
    }

    @PostMapping("/items")
    public ResponseEntity<HttpStatus> addDishToCart(@RequestParam("restaurantId") Integer restaurantId,
                                                    @RequestParam("dishId") Integer dishId,
                                                    @RequestParam("userId") Integer userId) {
        cartService.addDishToCart(restaurantId, dishId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<HttpStatus> deleteDishFromCart(@PathVariable int id) {
        cartService.deleteDishFromCart(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<HttpStatus> removeItemFromUserCart(@PathVariable("id") int dishId, int personId) {
        cartService.removeItem(dishId, personId);
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

    @ExceptionHandler
    private ResponseEntity<DefaultErrorResponse> handleException(DishNotFoundException error) {
        DefaultErrorResponse response = new DefaultErrorResponse(
                error.getMessage() + " Dish with id not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<DefaultErrorResponse> handleException(PersonNotFoundException ex) {
        DefaultErrorResponse errorResponse = new DefaultErrorResponse(
                ex.getMessage() + " Person with id not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
