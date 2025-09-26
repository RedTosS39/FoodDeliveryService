package ru.redtoss.kode.fooddeliveryservice.exceptions;

public class RestaurantNotCreatedException extends RuntimeException {
    public RestaurantNotCreatedException(String message) {
        super(message);
    }
}
