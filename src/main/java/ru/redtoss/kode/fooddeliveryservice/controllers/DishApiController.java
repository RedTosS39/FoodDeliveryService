package ru.redtoss.kode.fooddeliveryservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDTO;
import ru.redtoss.kode.fooddeliveryservice.services.FoodDishService;
import ru.redtoss.kode.fooddeliveryservice.services.RestaurantsService;
import ru.redtoss.kode.fooddeliveryservice.utils.DishNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.utils.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{id}/menu")
public class DishApiController implements ShowErrorMessage {
    private final RestaurantsService restaurantsService;
    private final FoodDishService foodDishService;

    @Autowired
    public DishApiController(RestaurantsService restaurantsService, FoodDishService foodDishService) {
        this.restaurantsService = restaurantsService;
        this.foodDishService = foodDishService;
    }

    @GetMapping
    public List<FoodDishDTO> findAll(@PathVariable int id) {
        return foodDishService.findAll(id);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createDish(@PathVariable int id,
                                                 @RequestBody @Valid FoodDishDTO foodDishDTO,
                                                 @RequestParam(name = "dishName", required = false) String dishName,
                                                 @RequestParam(name = "dishPrice", required = false) Integer dishPrice,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = showErrorMessage(bindingResult);
            throw new DishNotCreatedException(errorMsg);
        }

        if (dishPrice != null) {
            foodDishDTO.setDishPrice(dishPrice);
        }

        restaurantsService.createDish(id, foodDishDTO);
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@PathVariable int id, @RequestBody FoodDishDTO foodDishDTO) {
        if (foodDishDTO.getDishName() != null) {
            foodDishDTO.setDishName(foodDishDTO.getDishName());
        }
        if (foodDishDTO.getDishPrice() != null) {
            foodDishDTO.setDishPrice(foodDishDTO.getDishPrice());
        }

        foodDishService.update(id, foodDishDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{menu_id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id,
                                             @PathVariable int menu_id) {
        foodDishService.delete(menu_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(DishNotFoundException error) {
        ErrorResponse response = new ErrorResponse(
                error.getMessage() + " dish with id not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
