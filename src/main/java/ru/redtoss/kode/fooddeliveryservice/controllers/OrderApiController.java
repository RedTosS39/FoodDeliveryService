package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDTO;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodOrderDTO;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodDish;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrder;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;
import ru.redtoss.kode.fooddeliveryservice.services.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@ResponseBody
public class OrderApiController {

    private final OrdersService ordersService;

    @Autowired
    public OrderApiController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }


    @GetMapping()
    public List<FoodOrderDTO> showOrders(@RequestParam("userId") int userId) {
        return ordersService.showOrders(userId);
    }

    @GetMapping("/{id}")
    public List<FoodDishDTO> showOrder(@PathVariable("id") int id) {
        return ordersService.findOrderById(id);
    }

    @PostMapping
    private ResponseEntity<HttpStatus> createOrder(@RequestParam("id") int id) {
        ordersService.createOrder(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<HttpStatus> updateOrderStatus(@PathVariable("id") int id, @RequestParam("status") OrderStatus status) {

        if (status == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        ordersService.updateOrderStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
