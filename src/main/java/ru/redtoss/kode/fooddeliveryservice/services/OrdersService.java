package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDto;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodOrderDto;
import ru.redtoss.kode.fooddeliveryservice.entities.CartEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDishEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrderEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfileEntity;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;
import ru.redtoss.kode.fooddeliveryservice.repositories.*;
import ru.redtoss.kode.fooddeliveryservice.utils.DishNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.OrderNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrdersService implements ConvertEntity {

    private final ProfileRepository personRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;


    @Autowired
    public OrdersService(OrderRepository orderRepository,
                         ProfileRepository personRepository,
                         CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
        this.cartRepository = cartRepository;

    }

    public void updateOrderStatus(int order_Id, OrderStatus status) {
        Optional<FoodOrderEntity> order = orderRepository.findById(order_Id);
        if (order.isPresent()) {
            order.get().setOrderStatus(status);
            orderRepository.save(order.get());
        }
    }


    @Transactional
    public void createOrder(int personId) {
        Optional<PersonProfileEntity> optionalPersonProfile = personRepository.findById(personId);
        if (optionalPersonProfile.isPresent()) {
            PersonProfileEntity profile = optionalPersonProfile.get();
            CartEntity cartEntity = profile.getCartEntity();
            List<FoodDishEntity> dishesFromCart = cartEntity.getFoodDishEntities();

            if (cartEntity.countSum() < 300) {
                throw new DishNotFoundException("Order must be more then 300" + " Sum now:" + cartEntity.countSum());
            }

            if (dishesFromCart == null || dishesFromCart.isEmpty()) {
                throw new DishNotFoundException("This person doesn't have any food dish in the cart");
            }


            FoodOrderEntity order = new FoodOrderEntity();

            order.setFoodDishEntities(new ArrayList<>(dishesFromCart));
            order.setPeople(profile);
            order.setOrderStatus(OrderStatus.NEW);
            orderRepository.save(order);

            for (FoodDishEntity dish : dishesFromCart) {
                dish.setFoodOrderEntity(order);
                dish.setCartEntity(null);
            }

            profile.getFoodOrderEntityList().add(order);

            cartEntity.getFoodDishEntities().clear();
            cartRepository.save(cartEntity);

            profile.getFoodOrderEntityList().add(order);
            personRepository.save(profile);

        } else {
            throw new PersonNotFoundException();
        }
    }

    public List<FoodDishDto> findOrderById(int id) {
        Optional<FoodOrderEntity> optionalFoodOrder = orderRepository.findById(id);
        if (optionalFoodOrder.isPresent()) {
            FoodOrderEntity foodOrderEntity = optionalFoodOrder.get();
            return foodOrderEntity.getFoodDishEntities().stream().map(this::convertToFoodDishDTO).toList();
        }
        throw new OrderNotFoundException();
    }

    public List<FoodOrderDto> showOrders(int userId) {
        List<FoodOrderEntity> list = orderRepository.findByPeopleId(userId);
        System.out.println(list);
        System.out.println(list.stream()
                .map(this::tooFoodOrderDTO).toList());
        return list.stream().map(this::tooFoodOrderDTO).toList();
    }


    private FoodOrderDto tooFoodOrderDTO(FoodOrderEntity foodOrderEntity) {
        FoodOrderDto foodOrderDTO = new FoodOrderDto();
        if (foodOrderEntity.getFoodDishEntities() != null && !foodOrderEntity.getFoodDishEntities().isEmpty()) {
            List<FoodDishDto> list = foodOrderEntity.getFoodDishEntities()
                    .stream()
                    .map(this::convertToFoodDishDTO).toList();
            foodOrderDTO.setFoodDishDTOList(list);
            foodOrderDTO.setSum(list);
        } else {
            foodOrderDTO.setFoodDishDTOList(new ArrayList<>());
            foodOrderDTO.setSum(new ArrayList<>());
        }
        foodOrderDTO.setStatus(foodOrderEntity.getOrderStatus());
        return foodOrderDTO;
    }

}
