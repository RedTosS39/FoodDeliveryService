package ru.redtoss.kode.fooddeliveryservice.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.CartDTO;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDTO;
import ru.redtoss.kode.fooddeliveryservice.entities.Cart;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDish;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;
import ru.redtoss.kode.fooddeliveryservice.entities.Restaurant;
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

    public CartDTO findAll(int personId) {
        Optional<PersonProfile> optionalPersonProfile = profileRepository.findById(personId);
        if (optionalPersonProfile.isPresent()) {
            PersonProfile personProfile = optionalPersonProfile.get();
            Cart cart = personProfile.getCart();
            int total = cart.countSum();
            cart.setSum(total);

            List<FoodDishDTO> list = cart.getFoodDishes().stream()
                    .map(this::convertToFoodDishDTO)
                    .toList();

            return new CartDTO(list);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @Transactional
    public void removeItem(int dishId, int personId) {
        Optional<PersonProfile> optionalPersonProfile = profileRepository.findById(personId);
        if (optionalPersonProfile.isPresent()) {
            PersonProfile personProfile = optionalPersonProfile.get();
            Optional<FoodDish> optionalFoodDish = personProfile.getCart().getFoodDishes().stream().filter(it -> it.getId() == dishId).findFirst();

            if (optionalFoodDish.isPresent()) {
                FoodDish foodDish = optionalFoodDish.get();
                int newQty = foodDish.getQuantity() - 1;
                System.out.println("Dish after" + newQty);
                if (newQty < 1) {
                    personProfile.getCart().getFoodDishes().remove(foodDish);
                    foodDish.setCart(null);
                } else {
                   foodDish.setQuantity(newQty);
                }

                foodDishRepository.save(optionalFoodDish.get());
                profileRepository.save(personProfile);
            } else {
                throw new DishNotFoundException("Dish with id: " + dishId + " not found");
            }
        } else {
            throw new PersonNotFoundException();
        }
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

            if (!restaurant.getActive()) {
                throw new RestaurantNotCreatedException("Restaurant is close");
            }

            if (restaurant.getMenu().getDishes().stream().anyMatch(it -> it.getId() == dishId)) {
                Optional<FoodDish> currentDish = person.getCart()
                        .getFoodDishes()
                        .stream()
                        .filter(it -> it.getId() == dishId)
                        .findFirst();

                if (currentDish.isPresent()) {
                    currentDish.get().setQuantity(currentDish.get().getQuantity() + 1);
                    foodDishRepository.save(currentDish.get());
                } else {
                    foodDish.setQuantity(1);
                    person.getCart().getFoodDishes().add(foodDish);

                    foodDish.setCart(person.getCart());
                    foodDishRepository.save(foodDish);
                }

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
        if (personProfileOptional.isPresent()) {
            PersonProfile personProfile = personProfileOptional.get();
            personProfile.getCart().getFoodDishes().clear();
        }
    }
}
