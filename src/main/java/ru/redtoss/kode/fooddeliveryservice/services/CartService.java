package ru.redtoss.kode.fooddeliveryservice.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.CartDto;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDto;
import ru.redtoss.kode.fooddeliveryservice.entities.CartEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDishEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfileEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.RestaurantEntity;
import ru.redtoss.kode.fooddeliveryservice.repositories.CartRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.FoodDishRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.RestaurantRepository;
import ru.redtoss.kode.fooddeliveryservice.utils.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CartService implements ConvertEntity {

    private final ProfileRepository profileRepository;
    private final FoodDishRepository foodDishRepository;
    private final CartRepository cartRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public CartService(ProfileRepository profileRepository, FoodDishRepository foodDishRepository, CartRepository cartRepository, RestaurantRepository restaurantRepository) {
        this.profileRepository = profileRepository;
        this.foodDishRepository = foodDishRepository;
        this.cartRepository = cartRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public CartDto findAll(int personId) {
        Optional<PersonProfileEntity> optionalPersonProfile = profileRepository.findById(personId);
        if (optionalPersonProfile.isPresent()) {
            PersonProfileEntity personProfileEntity = optionalPersonProfile.get();
            CartEntity cartEntity = personProfileEntity.getCartEntity();
            int total = cartEntity.countSum();
            cartEntity.setSum(total);

            List<FoodDishDto> list = cartEntity.getFoodDishEntities().stream()
                    .map(this::convertToFoodDishDTO)
                    .toList();

            return new CartDto(list);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @Transactional
    public void removeItem(int dishId, int personId) {
        Optional<PersonProfileEntity> optionalPersonProfile = profileRepository.findById(personId);
        if (optionalPersonProfile.isPresent()) {
            PersonProfileEntity personProfileEntity = optionalPersonProfile.get();
            Optional<FoodDishEntity> optionalFoodDish = personProfileEntity.getCartEntity()
                    .getFoodDishEntities()
                    .stream()
                    .filter(it -> it.getId() == dishId)
                    .findFirst();

            if (optionalFoodDish.isPresent()) {
                FoodDishEntity foodDishEntity = optionalFoodDish.get();
                int newQty = foodDishEntity.getQuantity() - 1;
                log.info("Dish after{} ", newQty );
                if (newQty < 1) {
                    personProfileEntity.getCartEntity().getFoodDishEntities().remove(foodDishEntity);
                    foodDishEntity.setCartEntity(null);
                } else {
                    foodDishEntity.setQuantity(newQty);
                }

                foodDishRepository.save(optionalFoodDish.get());
                profileRepository.save(personProfileEntity);
            } else {
                throw new DishNotFoundException("Dish with id: " + dishId + " not found");
            }
        } else {
            throw new PersonNotFoundException();
        }
    }


    @Transactional
    public void addDishToCart(int restaurantId, int dishId, int userId) {
        Hibernate.initialize(RestaurantEntity.class);
        Hibernate.initialize(PersonProfileEntity.class);

        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Optional<FoodDishEntity> optionalFoodDish = foodDishRepository.findById(dishId);
        Optional<PersonProfileEntity> personProfileOptional = profileRepository.findById(userId);


        if (optionalFoodDish.isPresent() && personProfileOptional.isPresent() && optionalRestaurant.isPresent()) {

            RestaurantEntity restaurantEntity = optionalRestaurant.get();
            FoodDishEntity foodDishEntity = optionalFoodDish.get();
            PersonProfileEntity person = personProfileOptional.get();
            CartEntity cartEntity = personProfileOptional.get().getCartEntity();

            if (!restaurantEntity.getActive()) {
                throw new RestaurantNotCreatedException("Restaurant is close");
            }

            if (restaurantEntity.getMenu().getDishes().stream().anyMatch(it -> it.getId() == dishId) && cartEntity != null) {
                Optional<FoodDishEntity> currentDish = person.getCartEntity()
                        .getFoodDishEntities()
                        .stream()
                        .filter(it -> it.getId() == dishId)
                        .findFirst();

                if (currentDish.isPresent()) {
                    currentDish.get().setQuantity(currentDish.get().getQuantity() + 1);
                    foodDishRepository.save(currentDish.get());
                } else {
                    foodDishEntity.setQuantity(1);
                    person.getCartEntity().getFoodDishEntities().add(foodDishEntity);

                    foodDishEntity.setCartEntity(person.getCartEntity());
                    foodDishRepository.save(foodDishEntity);
                }

                cartRepository.save(person.getCartEntity());
            } else {
                throw new DishNotFoundException("Dish not found");
            }
        } else {
            throw new RestaurantNotFoundException();
        }
    }

    @Transactional
    public void deleteDishFromCart(int userId) {
        Optional<PersonProfileEntity> personProfileOptional = profileRepository.findById(userId);
        if (personProfileOptional.isPresent()) {
            PersonProfileEntity personProfileEntity = personProfileOptional.get();
            personProfileEntity.getCartEntity().getFoodDishEntities().clear();
        }
    }
}
