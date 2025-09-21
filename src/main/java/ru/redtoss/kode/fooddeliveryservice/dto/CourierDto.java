package ru.redtoss.kode.fooddeliveryservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.redtoss.kode.fooddeliveryservice.services.ProfileUpdater;

public class CourierDto implements ProfileUpdater {

    public CourierDto() {

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
