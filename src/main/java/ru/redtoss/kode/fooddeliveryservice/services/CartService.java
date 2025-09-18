package ru.redtoss.kode.fooddeliveryservice.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDish;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;
import ru.redtoss.kode.fooddeliveryservice.entities.Restaurant;
import ru.redtoss.kode.fooddeliveryservice.repositories.CartRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.FoodDishRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.RestaurantRepository;
import ru.redtoss.kode.fooddeliveryservice.utils.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotFoundException;

import java.util.Optional;

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


    @Transactional
    public void addDishToCart(int restaurantId, int dishId, int userId) {
        Hibernate.initialize(Restaurant.class);
        Hibernate.initialize(PersonProfile.class);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Optional<FoodDish> optionalFoodDish = foodDishRepository.findById(dishId);
        Optional<PersonProfile> personProfileOptional = profileRepository.findById(userId);

        if (optionalFoodDish.isPresent() && personProfileOptional.isPresent() && optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            FoodDish foodDish = optionalFoodDish.get();
            PersonProfile person = personProfileOptional.get();

            if (restaurant.getMenu().getDishes().contains(foodDish)) {
                person.getCart().getFoodDishes().add(foodDish);
                foodDish.setCart(person.getCart());

                foodDishRepository.save(foodDish);
                cartRepository.save(person.getCart());
            } else {
                throw new DishNotFoundException("Dish not found");
            }

        } else {
            throw new RestaurantNotFoundException();
        }
    }

    @Transactional
    public void deleteDishFromCart(int userId) {
        Optional<PersonProfile> personProfileOptional = profileRepository.findById(userId);
    }

}
