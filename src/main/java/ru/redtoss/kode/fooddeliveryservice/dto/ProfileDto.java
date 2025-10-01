package ru.redtoss.kode.fooddeliveryservice.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.redtoss.kode.fooddeliveryservice.entities.RestaurantEntity;
import ru.redtoss.kode.fooddeliveryservice.models.Role;
import ru.redtoss.kode.fooddeliveryservice.models.Status;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class ProfileDto {

    private int id;

    private String name;

    private Role role;

    private Status status;

    private Boolean isActive;

    private RestaurantEntity restaurantEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDto that = (ProfileDto) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedDate;

}
