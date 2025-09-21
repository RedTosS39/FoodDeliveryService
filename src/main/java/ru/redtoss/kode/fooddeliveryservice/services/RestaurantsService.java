package ru.redtoss.kode.fooddeliveryservice.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDto;
import ru.redtoss.kode.fooddeliveryservice.dto.RestaurantDto;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDishEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodMenuEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.RestaurantEntity;
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

    public List<RestaurantDto> findAll() {
        return restaurantRepository.findAll()
                .stream()
                .filter(RestaurantEntity::getActive
        ).map(this::convertToRestaurantDTO).collect(Collectors.toList());
    }


    public RestaurantDto findById(int id) {
        if (restaurantRepository.findById(id).isPresent()) {
            return convertToRestaurantDTO(restaurantRepository.findById(id).get());
        }
        throw new RestaurantNotFoundException();
    }

    @Transactional
    public void createRestaurant(RestaurantDto restaurantDTO) {
        RestaurantEntity restaurantEntity = convertToRestaurant(restaurantDTO);
        restaurantEntity.setName(restaurantDTO.getName());
        restaurantEntity.setActive(true);
        Hibernate.initialize(RestaurantEntity.class);

        FoodMenuEntity foodMenuEntity = new FoodMenuEntity();
        restaurantEntity.setMenu(foodMenuEntity);
        foodMenuEntity.setRestaurantEntity(restaurantEntity);
        foodMenuRepository.save(foodMenuEntity);
        restaurantRepository.save(restaurantEntity);
    }

    @Transactional
    public void createDish(int id, FoodDishDto dishDTO) {
        FoodDishEntity dish = convertToFoodDish(dishDTO);
        Optional<FoodMenuEntity> optionalFoodMenu = foodMenuRepository.findById(id);
        if (optionalFoodMenu.isPresent()) {
            FoodMenuEntity foodMenuEntity = optionalFoodMenu.get();
            if (foodMenuEntity.getDishes().stream()
                    .anyMatch(it -> it.getDishName().equals(dishDTO.getDishName()))) {
                dish.setQuantity(dishDTO.getDishQuantity());
            } else {
                dish.setFoodMenuEntity(foodMenuEntity);
                dish.setAvailable(true);
                dish.setPrice(dishDTO.getDishPrice());
                dish.setQuantity(dishDTO.getDishQuantity());
                foodMenuEntity.getDishes().add(dish);
            }
            foodDishRepository.save(dish);
            foodMenuRepository.save(foodMenuEntity);
        } else {
            throw new MenuNotCreatedException();
        }
    }

    @Transactional
    public void updateRestaurant(int id, RestaurantDto updater) {
        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            RestaurantEntity restaurantEntity = optionalRestaurant.get();
            restaurantEntity.setName(updater.getName());
            if (updater.getRating() != null) {
                restaurantEntity.setRating(updater.getRating());
            }

            if (updater.getActive() != null) {
                restaurantEntity.setActive(updater.getActive());
            }

            if (updater.getRestaurantType() != null) {
                restaurantEntity.setRestaurantType(updater.getRestaurantType());
            }

            restaurantRepository.save(restaurantEntity);

            System.out.println(restaurantEntity);
        } else {
            throw new RestaurantNotFoundException();
        }
    }

    @Transactional
    public void deleteRestaurant(int id) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            restaurant.get().setActive(false);
        } else {
            throw new RestaurantNotFoundException();
        }
    }
}
