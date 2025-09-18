package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDTO;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDish;
import ru.redtoss.kode.fooddeliveryservice.entities.Restaurant;
import ru.redtoss.kode.fooddeliveryservice.repositories.FoodDishRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.RestaurantRepository;
import ru.redtoss.kode.fooddeliveryservice.utils.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.RestaurantNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FoodDishService implements ConvertEntity {

    private final FoodDishRepository foodDishRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public FoodDishService(FoodDishRepository foodDishRepository1, RestaurantRepository restaurantRepository) {
        this.foodDishRepository = foodDishRepository1;
        this.restaurantRepository = restaurantRepository;
    }

    public List<FoodDishDTO> findAll(int id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            return optionalRestaurant.get().getMenu().getDishes().stream()
                    .filter(FoodDish::getIsAvailable)
                    .map(this::convertToFoodDishDTO).toList();
        } else {
            throw new RestaurantNotFoundException();
        }
    }

    @Transactional
    public void update(int id, FoodDishDTO foodDishDTO) {
        Optional<FoodDish> foodDish = foodDishRepository.findById(id);
        if (foodDish.isPresent()) {
            foodDish.get().setId(id);
            foodDish.get().setDishName(foodDishDTO.getDishName());
            foodDish.get().setPrice(foodDishDTO.getDishPrice());
        }
    }

    @Transactional
    public void delete(int id) {
        Optional<FoodDish> optionalFoodDish = foodDishRepository.findById(id);
        if (optionalFoodDish.isPresent()) {
            FoodDish foodDish = optionalFoodDish.get();
            foodDish.setAvailable(false);
            foodDishRepository.save(foodDish);
        } else {
            throw new DishNotFoundException("Dish with id " + id + " not found");
        }
    }
}
