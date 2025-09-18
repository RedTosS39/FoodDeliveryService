package ru.redtoss.kode.fooddeliveryservice.services;

import org.modelmapper.ModelMapper;
import ru.redtoss.kode.fooddeliveryservice.dto.*;
import ru.redtoss.kode.fooddeliveryservice.entities.*;

public interface ConvertEntity {

    ModelMapper modelMapper = new ModelMapper();

    default PersonProfile convertToPersonProfile(CourierDTO courierDTO) {
        return modelMapper.map(courierDTO, PersonProfile.class);
    }

    default PersonProfile convertToPersonProfile(ProfileUpdater profileDTO) {
        return modelMapper.map(profileDTO, PersonProfile.class);
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

    default Restaurant convertToRestaurant(RestaurantDTO profileDTO) {
        return modelMapper.map(profileDTO, Restaurant.class);
    }

    default RestaurantDTO convertToRestaurantDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    default FoodDish convertToFoodDish(FoodDishDTO foodDishDTO) {
        return modelMapper.map(foodDishDTO, FoodDish.class);
    }

    default FoodDishDTO convertToFoodDishDTO(FoodDish foodDish) {
        return modelMapper.map(foodDish, FoodDishDTO.class);
    }

    default FoodMenu convertToFoodMenu(FoodMenuDTO foodMenuDTO) {
        return modelMapper.map(foodMenuDTO, FoodMenu.class);
    }

    default Cart convertToCart(CartDTO cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    default CartDTO convertToCartDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }
}
