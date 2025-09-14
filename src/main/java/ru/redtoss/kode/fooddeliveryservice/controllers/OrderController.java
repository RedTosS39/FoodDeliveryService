package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.redtoss.kode.fooddeliveryservice.services.OrdersService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrdersService ordersService;

    @Autowired
    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }


    @GetMapping
    public String showOrders(Model model) {
        model.addAttribute("orders", ordersService.findOrders());
        return "orders/orders";
    }
}
