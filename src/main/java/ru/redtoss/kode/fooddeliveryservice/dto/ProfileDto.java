package ru.redtoss.kode.fooddeliveryservice.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.redtoss.kode.fooddeliveryservice.entities.RestaurantEntity;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.Status;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDto {

    private String name;

    private Role role;

    private Status status;

    private Boolean isActive;

    private RestaurantEntity restaurantEntity;



    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedDate;

}
