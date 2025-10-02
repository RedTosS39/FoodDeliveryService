package ru.redtoss.kode.fooddeliveryservice.services;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.redtoss.kode.fooddeliveryservice.dto.PaymentDto;
import ru.redtoss.kode.fooddeliveryservice.entities.FoodOrderEntity;
import ru.redtoss.kode.fooddeliveryservice.entities.PaymentEntity;
import ru.redtoss.kode.fooddeliveryservice.models.OrderStatus;
import ru.redtoss.kode.fooddeliveryservice.repositories.OrderRepository;
import ru.redtoss.kode.fooddeliveryservice.repositories.PaymentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class PaymentServiceTest {

    private final static int PAYMENT_ID = 1;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    PaymentService paymentService;

    private final ConvertEntity convertEntity = new ConvertEntity() {
        @Override
        public PaymentDto convertToPaymentDto(PaymentEntity paymentEntity) {
            return ConvertEntity.super.convertToPaymentDto(paymentEntity);
        }
    };

    @Test
    void PaymentService_FindPaymentById() {
        PaymentEntity expected = PaymentEntity.builder()
                .id(PAYMENT_ID)
                .foodOrder(null)
                .build();

        PaymentDto expectedDto = convertEntity.convertToPaymentDto(expected);
        when(paymentRepository.findById(PAYMENT_ID)).thenReturn(Optional.of(expected));
        PaymentDto actual = paymentService.findPaymentById(PAYMENT_ID);
        assertEquals(expectedDto, actual);
    }

    @Test
    void PaymentService_Create_Payment() {
        PaymentEntity expected = PaymentEntity.builder()
                .id(PAYMENT_ID)
                .foodOrder(null)
                .build();

        FoodOrderEntity foodOrder = new FoodOrderEntity();
        foodOrder.setId(PAYMENT_ID);
        when(orderRepository.findById(1)).thenReturn(Optional.of(foodOrder));
        when(paymentRepository.findById(PAYMENT_ID)).thenReturn(Optional.ofNullable(expected));
        when(paymentRepository.save(Mockito.any(PaymentEntity.class))).thenReturn(expected);

        paymentService.createPayments(PAYMENT_ID);

        assertNotNull(paymentService.findPaymentById(PAYMENT_ID));
    }
}