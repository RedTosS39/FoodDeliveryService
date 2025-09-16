package ru.redtoss.kode.fooddeliveryservice.controllers.dto;


import jakarta.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.Status;

import java.time.LocalDateTime;

public class ProfileDTO {

    private String name;

    private Role role;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }


}
