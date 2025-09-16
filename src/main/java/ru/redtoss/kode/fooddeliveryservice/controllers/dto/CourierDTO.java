package ru.redtoss.kode.fooddeliveryservice.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourierDTO {

    public CourierDTO() {

    }

    @NotNull(message = "Enter the username")
    @Size(min = 2, max = 10, message = "Размер от 2 до 10 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
