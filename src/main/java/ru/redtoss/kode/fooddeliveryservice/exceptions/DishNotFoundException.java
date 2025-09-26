package ru.redtoss.kode.fooddeliveryservice.exceptions;

public class DishNotFoundException extends RuntimeException {

    public DishNotFoundException(String message) {
        super(message);
    }
}
