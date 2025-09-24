package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodDishDto;
import ru.redtoss.kode.fooddeliveryservice.dto.FoodOrderDto;
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
    public List<FoodOrderDto> showOrders(@RequestParam("userId") int userId) {
        return ordersService.showOrders(userId);
    }

    @GetMapping("/{id}")
    public List<FoodDishDto> showOrder(@PathVariable("id") int id) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus>  deleteOrder(@PathVariable("id") int id) {
        ordersService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
