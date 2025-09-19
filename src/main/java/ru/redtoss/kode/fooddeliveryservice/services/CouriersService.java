package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.entities.Courier;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrder;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;
import ru.redtoss.kode.fooddeliveryservice.repositories.CourierRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.OrderRepository;
import ru.redtoss.kode.fooddeliveryservice.utils.PersonNotCreatedException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CouriersService {
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CouriersService(CourierRepository courierRepository, OrderRepository orderRepository) {
        this.courierRepository = courierRepository;
        this.orderRepository = orderRepository;
    }


    @Transactional
    public void assignOrderToCourier(int courierId, int orderId) {
        Optional<Courier> optionalCourier = courierRepository.findById(courierId);
        Optional<FoodOrder> optionalOrder = orderRepository.findById(orderId);

        if (optionalCourier.isPresent() && optionalOrder.isPresent()) {
            Courier courier = optionalCourier.get();
            FoodOrder order = optionalOrder.get();
            courier.setOrderCount(courier.getOrderCount() + 1);
            if (courier.getOrderCount() > 3) {
                throw new PersonNotCreatedException("Courier has maximum of orders (3)");
            }

            if (order.getOrderStatus().equals(OrderStatus.READY)) {
                courier.getFoodOrders().add(order);
                order.setCourier(courier);
                order.setOrderStatus(OrderStatus.CONFIRMED);
            } else {
                throw new PersonNotCreatedException("Order has not been confirmed");
            }

            orderRepository.save(order);
            courierRepository.save(courier);
        }
    }
}
