package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public void addDishToCart(@RequestParam("restaurantId") Integer restaurantId,
                              @RequestParam("dishId") Integer dishId,
                              @RequestParam("userId") Integer userId) {

        cartService.addDishToCart(restaurantId, dishId, userId);
    }

    @DeleteMapping("/items/{id}")
    public void deleteDishFromCart(@PathVariable int id) {
        cartService.deleteDishFromCart(id);
    }
}
