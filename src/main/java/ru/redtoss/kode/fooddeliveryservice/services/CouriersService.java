package ru.redtoss.kode.fooddeliveryservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.CourierDto;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodOrderDto;
import ru.redtoss.kode.fooddeliveryservice.entities.CourierEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrderEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PersonProfileEntity;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;
import ru.redtoss.kode.fooddeliveryservice.repositories.CourierRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.OrderRepository;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PersonNotCreatedException;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PersonNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.repositories.ProfileRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CouriersService implements ConvertEntity {
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public CouriersService(CourierRepository courierRepository,
                           OrderRepository orderRepository, ProfileRepository profileRepository) {
        this.courierRepository = courierRepository;
        this.orderRepository = orderRepository;
        this.profileRepository = profileRepository;
    }

    public List<FoodOrderDto> findAllOrders(int id) {
        CourierEntity courier = courierRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return courier.getFoodOrderEntities()
                .stream()
                .map(this::convertToFoodOrderDTO)
                .toList();
    }


    @Transactional
    public void update(int id, ProfileUpdater updater) {
        PersonProfileEntity personProfileEntity = profileRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personProfileEntity.setId(id);
        personProfileEntity.setName(updater.getName());
        personProfileEntity.setUpdatedDate(LocalDateTime.now());
        profileRepository.save(personProfileEntity);
        log.info("Person profile updated");
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
