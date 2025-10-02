package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redtoss.kode.fooddeliveryservice.services.PaymentService;

@RestController
@RequestMapping("/payments")
@ResponseBody
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpStatus> findById(@PathVariable int id) {
        paymentService.findPaymentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> createPayment(@RequestParam("orderId") int orderId) {
        paymentService.createPayments(orderId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
