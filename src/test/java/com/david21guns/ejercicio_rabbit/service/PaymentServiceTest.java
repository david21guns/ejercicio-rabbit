package com.david21guns.ejercicio_rabbit.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.david21guns.ejercicio_rabbit.config.RabbitMQConfig;
import com.david21guns.ejercicio_rabbit.dto.PaymentDTO;
import com.david21guns.ejercicio_rabbit.dto.PaymentMapper;
import com.david21guns.ejercicio_rabbit.exception.ResourceNotFoundException;
import com.david21guns.ejercicio_rabbit.model.Payment;
import com.david21guns.ejercicio_rabbit.model.StatusChangedMessage;
import com.david21guns.ejercicio_rabbit.repository.PaymentRepository;

class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        PaymentDTO paymentDTO = new PaymentDTO();
        Payment payment = PaymentMapper.mapper.paymentDtoToPayment(paymentDTO);
        when(repository.save(payment)).thenReturn(payment);

        paymentService.createPayment(paymentDTO);

        verify(repository, times(1)).save(payment);
    }

    @Test
    void testUpdatePayment_StatusChanged() {
        String paymentId = "123";
        PaymentDTO updatedPaymentDTO = new PaymentDTO();
        updatedPaymentDTO.setEstatus("Completed");
        Payment existingPayment = new Payment();
        existingPayment.setEstatus("Pending");
        existingPayment.setId(paymentId);

        when(repository.findById(paymentId)).thenReturn(Optional.of(existingPayment));

        paymentService.updatePayment(paymentId, updatedPaymentDTO);

        StatusChangedMessage expectedMessage = new StatusChangedMessage(paymentId, "Completed");
        verify(repository, times(1)).save(existingPayment);
        verify(rabbitTemplate, times(1)).convertAndSend(RabbitMQConfig.EXCHANGE, "", expectedMessage);
    }

    @Test
    void testUpdatePayment_NotFound() {
        String paymentId = "123";
        PaymentDTO updatedPaymentDTO = new PaymentDTO();

        when(repository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.updatePayment(paymentId, updatedPaymentDTO);
        });

        verify(repository, never()).save(any(Payment.class));
        verify(rabbitTemplate, never()).convertAndSend("", "", "");
    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(repository.findAll()).thenReturn(payments);

        List<PaymentDTO> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        String paymentId = "123";
        Payment payment = new Payment();
        when(repository.findById(paymentId)).thenReturn(Optional.of(payment));

        PaymentDTO result = paymentService.getById(paymentId);

        assertNotNull(result);
        verify(repository, times(1)).findById(paymentId);
    }

    @Test
    void testGetById_NotFound() {
        String paymentId = "123";
        when(repository.findById(paymentId)).thenReturn(Optional.empty());

        PaymentDTO result = paymentService.getById(paymentId);

        assertNull(result);
        verify(repository, times(1)).findById(paymentId);
    }

    @Test
    void testGetEstatusById_Found() {
        String paymentId = "123";
        Payment payment = new Payment();
        payment.setEstatus("Pending");
        when(repository.findById(paymentId)).thenReturn(Optional.of(payment));

        String result = paymentService.getEstatusById(paymentId);

        assertEquals("Pending", result);
        verify(repository, times(1)).findById(paymentId);
    }

    @Test
    void testGetEstatusById_NotFound() {
        String paymentId = "123";
        when(repository.findById(paymentId)).thenReturn(Optional.empty());

        String result = paymentService.getEstatusById(paymentId);

        assertNull(result);
        verify(repository, times(1)).findById(paymentId);
    }

    @Test
    void testGetPaymentsByEmisor() {
        String emisor = "User123";
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(repository.findByEmisorPago(emisor)).thenReturn(payments);

        List<PaymentDTO> result = paymentService.getPaymentsByEmisor(emisor);

        assertEquals(2, result.size());
        verify(repository, times(1)).findByEmisorPago(emisor);
    }
}