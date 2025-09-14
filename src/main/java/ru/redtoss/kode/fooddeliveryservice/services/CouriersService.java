package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.entities.Courier;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrder;
import ru.redtoss.kode.fooddeliveryservice.repositories.CourierRepository;

import java.util.Optional;

@Service
public class CouriersService {
    private final CourierRepository courierRepository;

    @Autowired
    public CouriersService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }


    @Transactional
    public void assignOrderToCourier(int courier_id, FoodOrder foodOrder) {
        Optional<Courier> optionalCourier = courierRepository.findById(courier_id);
        if (optionalCourier.isPresent()) {
            Courier courier = optionalCourier.get();
            courier.orderCount();
            if (courier.orderCount() < 3) {
                courier.setFoodOrder(foodOrder);
            } else {
                System.out.println("too much order for courier");
            }
        }
    }
}
