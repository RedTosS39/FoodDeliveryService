package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDTO;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodOrderDTO;
import ru.redtoss.kode.fooddeliveryservice.entities.Cart;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDish;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrder;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfile;
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
        Optional<FoodOrder> order = orderRepository.findById(order_Id);
        if (order.isPresent()) {
            order.get().setOrderStatus(status);
            orderRepository.save(order.get());
        }
    }


    @Transactional
    public void createOrder(int personId) {
        Optional<PersonProfile> optionalPersonProfile = personRepository.findById(personId);
        if (optionalPersonProfile.isPresent()) {
            PersonProfile profile = optionalPersonProfile.get();
            Cart cart = profile.getCart();
            List<FoodDish> dishesFromCart = cart.getFoodDishes();

            if (cart.countSum() < 300) {
                throw new DishNotFoundException("Order must be more then 300" + " Sum now:" + cart.countSum());
            }

            if (dishesFromCart == null || dishesFromCart.isEmpty()) {
                throw new DishNotFoundException("This person doesn't have any food dish in the cart");
            }


            FoodOrder order = new FoodOrder();

            order.setFoodDishes(new ArrayList<>(dishesFromCart));
            order.setPeople(profile);
            order.setOrderStatus(OrderStatus.NEW);
            orderRepository.save(order);

            for (FoodDish dish : dishesFromCart) {
                dish.setFoodOrder(order);
                dish.setCart(null);
            }

            profile.getFoodOrderList().add(order);

            cart.getFoodDishes().clear();
            cartRepository.save(cart);

            profile.getFoodOrderList().add(order);
            personRepository.save(profile);

        } else {
            throw new PersonNotFoundException();
        }
    }

    public List<FoodDishDTO> findOrderById(int id) {
        Optional<FoodOrder> optionalFoodOrder = orderRepository.findById(id);
        if (optionalFoodOrder.isPresent()) {
            FoodOrder foodOrder = optionalFoodOrder.get();
            return foodOrder.getFoodDishes().stream().map(this::convertToFoodDishDTO).toList();
        }
        throw new OrderNotFoundException();
    }

    public List<FoodOrderDTO> showOrders(int userId) {
        List<FoodOrder> list = orderRepository.findByPeopleId(userId);
        System.out.println(list);
        System.out.println(list.stream().map(this::tooFoodOrderDTO).toList());
        return list.stream().map(this::tooFoodOrderDTO).toList();
    }


    private FoodOrderDTO tooFoodOrderDTO(FoodOrder foodOrder) {
        FoodOrderDTO foodOrderDTO = new FoodOrderDTO();
        if (foodOrder.getFoodDishes() != null && !foodOrder.getFoodDishes().isEmpty()) {
            List<FoodDishDTO> list = foodOrder.getFoodDishes().stream()
                    .map(this::convertToFoodDishDTO).toList();
            foodOrderDTO.setFoodDishDTOList(list);
            foodOrderDTO.setSum(list);
        } else {
            foodOrderDTO.setFoodDishDTOList(new ArrayList<>());
            foodOrderDTO.setSum(new ArrayList<>());
        }
        foodOrderDTO.setStatus(foodOrder.getOrderStatus());
        return foodOrderDTO;
    }

}
