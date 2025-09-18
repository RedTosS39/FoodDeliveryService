package ru.redtoss.kode.fooddeliveryservice.utils;

public class RestaurantNotCreatedException extends RuntimeException {
    public RestaurantNotCreatedException(String message) {
        super(message);
    }
}
