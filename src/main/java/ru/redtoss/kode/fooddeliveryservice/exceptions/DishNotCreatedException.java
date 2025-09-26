package ru.redtoss.kode.fooddeliveryservice.exceptions;

public class DishNotCreatedException extends RuntimeException {
    public DishNotCreatedException(String message) {
        super(message);
    }
}
