package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.modelmapper.ModelMapper;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.CourierDTO;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.PersonDTO;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.ProfileDTO;
import ru.redtoss.kode.fooddeliveryservice.controllers.dto.ProfileUpdater;
import ru.redtoss.kode.fooddeliveryservice.entities.Courier;
import ru.redtoss.kode.fooddeliveryservice.entities.Person;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;

public interface ConvertEntity {

    ModelMapper modelMapper = new ModelMapper();

    default PersonProfile convertToPersonProfile(CourierDTO courierDTO) {
        return modelMapper.map(courierDTO, PersonProfile.class);
    }

    default Courier converToCourier(ProfileUpdater profileUpdater) {
        return modelMapper.map(profileUpdater, Courier.class);
    }

    default ProfileDTO converToProfileDTO(PersonProfile person) {
        return modelMapper.map(person, ProfileDTO.class);
    }

    default Person convertToPerson(ProfileUpdater personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    default PersonProfile convertToPersonProfile(ProfileUpdater profileDTO) {
        return modelMapper.map(profileDTO, PersonProfile.class);
    }

}
