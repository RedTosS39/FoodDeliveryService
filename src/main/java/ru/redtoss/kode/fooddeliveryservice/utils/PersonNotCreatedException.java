package ru.redtoss.kode.fooddeliveryservice.utils;

public class PersonNotCreatedException extends RuntimeException {
    public PersonNotCreatedException(String message) {
        super(message);
    }
}
