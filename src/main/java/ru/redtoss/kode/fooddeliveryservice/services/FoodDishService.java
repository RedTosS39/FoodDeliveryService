package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDto;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDishEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.RestaurantEntity;
import ru.redtoss.kode.fooddeliveryservice.repositories.FoodDishRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.RestaurantRepository;
import ru.redtoss.kode.fooddeliveryservice.exceptions.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.exceptions.RestaurantNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class FoodDishService implements ConvertEntity {

    private final FoodDishRepository foodDishRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public FoodDishService(FoodDishRepository foodDishRepository1, RestaurantRepository restaurantRepository) {
        this.foodDishRepository = foodDishRepository1;
        this.restaurantRepository = restaurantRepository;
    }

    public List<FoodDishDto> findAll(int id) {
        RestaurantEntity optionalRestaurant = restaurantRepository.findById(id).orElseThrow(RestaurantNotFoundException::new);
        return optionalRestaurant
                .getMenu()
                .getDishes()
                .stream()
                .filter(FoodDishEntity::getAvailable)
                .map(this::convertToFoodDishDTO).toList();
    }

    @Transactional
    public void update(int id, FoodDishDto foodDishDTO) {
        FoodDishEntity foodDish = foodDishRepository.findById(id).orElseThrow(() -> new DishNotFoundException("dish with id: " + id + " not found"));
        foodDish.setId(id);
        foodDish.setDishName(foodDishDTO.getDishName());
        foodDish.setPrice(foodDishDTO.getDishPrice());
    }

    @Transactional
    public void delete(int id) {
        Optional<FoodDishEntity> optionalFoodDish = foodDishRepository.findById(id);
        if (optionalFoodDish.isPresent()) {
            FoodDishEntity foodDishEntity = optionalFoodDish.get();
            foodDishEntity.setAvailable(false);
            foodDishRepository.save(foodDishEntity);
        } else {
            throw new DishNotFoundException("Dish with id " + id + " not found");
        }
    }
}
