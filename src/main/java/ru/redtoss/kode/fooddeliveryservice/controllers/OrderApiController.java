package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.redtoss.kode.fooddeliveryservice.services.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrderApiController {

    private final OrdersService ordersService;

    @Autowired
    public OrderApiController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }


    @GetMapping
    public String showOrders(Model model) {
        model.addAttribute("orders", ordersService.findOrders());
        return "orders/orders";
    }
}
