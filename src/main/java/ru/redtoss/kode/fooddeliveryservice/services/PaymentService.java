package ru.redtoss.kode.fooddeliveryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redtoss.kode.fooddeliveryservice.dto.PaymentDto;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrderEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PaymentEntity;
import ru.redtoss.kode.fooddeliveryservice.exceptions.OrderNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.exceptions.PaymentNotFoundException;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;
import ru.redtoss.kode.fooddeliveryservice.repositories.OrderRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.PaymentRepository;

@Service
public class PaymentService implements ConvertEntity {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public PaymentDto findPaymentById(int id) {
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        return convertToPaymentDto(payment);
    }


    @Transactional
    public void createPayments(int orderId) {
        FoodOrderEntity order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        PaymentEntity payment = PaymentEntity.builder()
                .foodOrder(order)
                .status(true)
                .build();
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPayment(payment);
        orderRepository.save(order);
        paymentRepository.save(payment);
    }
}
