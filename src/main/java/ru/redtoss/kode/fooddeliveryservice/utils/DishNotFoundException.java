package ru.redtoss.kode.fooddeliveryservice.utils;

public class DishNotFoundException extends RuntimeException {

    public DishNotFoundException(String message) {
        super(message);
    }
}
