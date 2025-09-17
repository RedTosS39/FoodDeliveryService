package ru.redtoss.kode.fooddeliveryservice.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourierDTO implements ProfileUpdater{

    public CourierDTO() {

    }

    @NotNull(message = "Enter the username")
    @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов")
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
