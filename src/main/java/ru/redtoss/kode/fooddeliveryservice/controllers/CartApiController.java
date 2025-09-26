package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.CartDto;
import ru.redtoss.kode.fooddeliveryservice.services.CartService;

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
    public CartDto findAll(@RequestParam("personId") Integer personId) {
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

}
