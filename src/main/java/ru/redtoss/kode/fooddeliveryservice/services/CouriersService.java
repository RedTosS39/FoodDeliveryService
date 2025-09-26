package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodOrderDto;
import ru.redtoss.kode.fooddeliveryservice.entities.CourierEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrderEntity;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;
import ru.redtoss.kode.fooddeliveryservice.repositories.CourierRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.OrderRepository;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PersonNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PersonNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CouriersService implements ConvertEntity {
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CouriersService(CourierRepository courierRepository,
                           OrderRepository orderRepository) {
        this.courierRepository = courierRepository;
        this.orderRepository = orderRepository;
    }

    public List<FoodOrderDto> findAllOrders(int id) {
        CourierEntity courier = courierRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return courier.getFoodOrderEntities()
                .stream()
                .map(this::convertToFoodOrderDTO)
                .toList();
    }


    @Transactional
    public void assignOrderToCourier(int courierId, int orderId) {
        Optional<CourierEntity> optionalCourier = courierRepository.findById(courierId);
        Optional<FoodOrderEntity> optionalOrder = orderRepository.findById(orderId);

        if (optionalCourier.isPresent() && optionalOrder.isPresent()) {
            CourierEntity courierEntity = optionalCourier.get();
            FoodOrderEntity order = optionalOrder.get();
            courierEntity.setOrderCount(courierEntity.getOrderCount() + 1);
            if (courierEntity.getOrderCount() > 3) {
                throw new PersonNotCreatedException("Courier has maximum of orders (3)");
            }

            if (order.getOrderStatus().equals(OrderStatus.READY)) {
                courierEntity.getFoodOrderEntities().add(order);
                order.setCourierEntity(courierEntity);
                order.setOrderStatus(OrderStatus.CONFIRMED);
                order.setCourierEntity(courierEntity);
            } else {
                throw new PersonNotCreatedException("Order has not been confirmed");
            }

            orderRepository.save(order);
            courierRepository.save(courierEntity);
        }
    }
}
