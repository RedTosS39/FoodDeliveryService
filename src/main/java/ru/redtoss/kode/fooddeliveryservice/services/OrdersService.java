package ru.redtoss.kode.fooddeliveryservice.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.entities.Courier;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrder;
import ru.redtoss.kode.fooddeliveryservice.entities.Person;
import ru.redtoss.kode.fooddeliveryservice.repositories.CourierRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.OrderRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrdersService {

    private final PersonRepository personRepository;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    @Autowired
    public OrdersService(OrderRepository orderRepository, PersonRepository personRepository, CourierRepository courierRepository) {
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
        this.courierRepository = courierRepository;
    }

    public List<FoodOrder> findOrders() {
        return orderRepository.findAll();
    }


    public Optional<FoodOrder> findOrderById(int id) {
    return     orderRepository.findById(id);
    }

    public void assignOrder(int person_id, int order_id) {
        Optional<Person> optionalPerson = personRepository.findById(person_id);
        Optional<FoodOrder> optionalFoodOrder = orderRepository.findById(order_id);
        if (optionalPerson.isPresent() && optionalFoodOrder.isPresent()) {
            Person person = optionalPerson.get();
            FoodOrder order = optionalFoodOrder.get();
            List<FoodOrder> list = new ArrayList<>();
            list.add(order);
            person.setFoodOrder(list);
        }
    }
}
