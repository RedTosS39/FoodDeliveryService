package ru.redtoss.kode.fooddeliveryservice.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDTO;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodMenuDTO;
import ru.redtoss.kode.fooddeliveryservice.dto.RestaurantDTO;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDish;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodMenu;
import ru.redtoss.kode.fooddeliveryservice.entities.Restaurant;
import ru.redtoss.kode.fooddeliveryservice.repositories.FoodDishRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.FoodMenuRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.RestaurantRepository;
import ru.redtoss.kode.fooddeliveryservice.utils.MenuNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RestaurantsService implements ConvertEntity {

    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final FoodDishRepository foodDishRepository;

    @Autowired
    public RestaurantsService(RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, FoodDishRepository foodDishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.foodDishRepository = foodDishRepository;
    }

    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll().stream().filter(Restaurant::getActive
        ).map(this::convertToRestaurantDTO).collect(Collectors.toList());
    }


    public RestaurantDTO findById(int id) {
        if (restaurantRepository.findById(id).isPresent()) {
            return convertToRestaurantDTO(restaurantRepository.findById(id).get());
        }
        throw new RestaurantNotFoundException();
    }

    @Transactional
    public void createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = convertToRestaurant(restaurantDTO);
        restaurant.setName(restaurantDTO.getName());
        restaurant.setActive(true);
        restaurantRepository.save(restaurant);
    }



    @Transactional
    public void createMenu(int id, FoodMenuDTO foodMenuDTO) {
        Hibernate.initialize(Restaurant.class);
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        FoodMenu foodMenu = convertToFoodMenu(foodMenuDTO);

        if (restaurant.isPresent()) {
            Restaurant rest = restaurant.get();
            foodMenu.setRestaurant(rest);
            rest.setId(restaurant.get().getId());
            rest.setMenu(foodMenu);
            restaurantRepository.save(rest);
            foodMenuRepository.save(foodMenu);
        } else {
            throw new MenuNotCreatedException();
        }
    }

    @Transactional
    public void createDish(int id, FoodDishDTO dishDTO) {
        FoodDish dish = convertToFoodDish(dishDTO);
        Optional<FoodMenu> optionalFoodMenu = foodMenuRepository.findById(id);
        if (optionalFoodMenu.isPresent()) {
            FoodMenu foodMenu = optionalFoodMenu.get();
            dish.setFoodMenu(foodMenu);
            dish.setPrice(dishDTO.getDishPrice());
            foodMenu.getDishes().add(dish);

            foodDishRepository.save(dish);
            System.out.println("Menu: " + foodMenu.getDishes());
            System.out.println("Dish: " + dish);
        } else {
            throw new MenuNotCreatedException();
        }
    }

    @Transactional
    public void updateRestaurant(int id, RestaurantDTO updater) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setName(updater.getName());
            if (updater.getRating() != null) {
                restaurant.setRating(updater.getRating());
            }

            if (updater.getActive() != null) {
                restaurant.setActive(updater.getActive());
            }

            if (updater.getRestaurantType() != null) {
                restaurant.setRestaurantType(updater.getRestaurantType());
            }

            restaurantRepository.save(restaurant);

            System.out.println(restaurant);
        } else {
            throw new RestaurantNotFoundException();
        }
    }

    @Transactional
    public void deleteRestaurant(int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            restaurant.get().setActive(false);
        } else {
            throw new RestaurantNotFoundException();
        }
    }
}
