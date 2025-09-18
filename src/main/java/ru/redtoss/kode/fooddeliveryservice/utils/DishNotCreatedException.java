package ru.redtoss.kode.fooddeliveryservice.utils;

public class DishNotCreatedException extends RuntimeException {
    public DishNotCreatedException(String message) {
        super(message);
    }
}
