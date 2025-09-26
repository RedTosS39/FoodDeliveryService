package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.redtoss.kode.fooddeliveryservice.exceptions.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PersonNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.exceptions.RestaurantNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.DefaultErrorResponse;


@ControllerAdvice
public class HandleException {

    private ResponseEntity<DefaultErrorResponse> getResponse(RuntimeException error, String entityName) {
        DefaultErrorResponse response = new DefaultErrorResponse(
                error.getMessage() +  " " + entityName + "  with id not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    private ResponseEntity<DefaultErrorResponse> handleException(RestaurantNotFoundException error) {
        return getResponse(error, "Restaurant");
    }

    @ExceptionHandler(DishNotFoundException.class)
    private ResponseEntity<DefaultErrorResponse> handleException(DishNotFoundException error) {
        return getResponse(error, "Dish");
    }

    @ExceptionHandler(PersonNotFoundException.class)
    private ResponseEntity<DefaultErrorResponse> handleException(PersonNotFoundException ex) {
        return getResponse(ex, "Person");
    }
}
