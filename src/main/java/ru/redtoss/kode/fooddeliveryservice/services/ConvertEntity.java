package ru.redtoss.kode.fooddeliveryservice.services;

import org.modelmapper.ModelMapper;
import ru.redtoss.kode.fooddeliveryservice.dto.*;
import ru.redtoss.kode.fooddeliveryservice.entities.*;

public interface ConvertEntity {

    ModelMapper modelMapper = new ModelMapper();

    default PersonProfileEntity convertToPersonProfile(CourierDto courierDTO) {
        return modelMapper.map(courierDTO, PersonProfileEntity.class);
    }

    default PersonProfileEntity convertToPersonProfile(ProfileUpdater profileDTO) {
        return modelMapper.map(profileDTO, PersonProfileEntity.class);
    }

    default CourierEntity convertToCourierEntity(ProfileUpdater profileUpdater) {
        return modelMapper.map(profileUpdater, CourierEntity.class);
    }

    default ProfileDto converToProfileDTO(PersonProfileEntity person) {
        return modelMapper.map(person, ProfileDto.class);
    }

    default PersonEntity convertToPerson(ProfileUpdater personDTO) {
        return modelMapper.map(personDTO, PersonEntity.class);
    }

    default PersonDto converToPersonDTO(ProfileUpdater person) {
        return modelMapper.map(person, PersonDto.class);
    }

    default RestaurantEntity convertToRestaurant(RestaurantDto profileDTO) {
        return modelMapper.map(profileDTO, RestaurantEntity.class);
    }

    default RestaurantDto convertToRestaurantDTO(RestaurantEntity restaurantEntity) {
        return modelMapper.map(restaurantEntity, RestaurantDto.class);
    }

    default FoodDishEntity convertToFoodDish(FoodDishDto foodDishDTO) {
        return modelMapper.map(foodDishDTO, FoodDishEntity.class);
    }

    default FoodDishDto convertToFoodDishDTO(FoodDishEntity foodDishEntity) {
        return modelMapper.map(foodDishEntity, FoodDishDto.class);
    }

    default FoodMenuEntity convertToFoodMenu(FoodMenuDto foodMenuDTO) {
        return modelMapper.map(foodMenuDTO, FoodMenuEntity.class);
    }

    default CartEntity convertToCart(CartDto cartDTO) {
        return modelMapper.map(cartDTO, CartEntity.class);
    }

    default CartDto convertToCartDTO(CartEntity cartEntity) {

        return modelMapper.map(cartEntity, CartDto.class);
    }

    default FoodOrderDto convertToFoodOrderDTO(FoodOrderEntity foodOrderEntity) {
        return modelMapper.map(foodOrderEntity, FoodOrderDto.class);
    }

    default FoodOrderEntity convertToFoodOrder(FoodOrderDto foodOrderDTO) {
        return modelMapper.map(foodOrderDTO, FoodOrderEntity.class);
    }

    default PaymentDto convertToPaymentDto(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, PaymentDto.class);
    }
}
