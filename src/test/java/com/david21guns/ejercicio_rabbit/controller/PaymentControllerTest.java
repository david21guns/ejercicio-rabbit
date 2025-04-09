package com.david21guns.ejercicio_rabbit.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import com.david21guns.ejercicio_rabbit.dto.PaymentDTO;
import com.david21guns.ejercicio_rabbit.service.PaymentService;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        PaymentDTO paymentDTO = new PaymentDTO();
        doNothing().when(paymentService).createPayment(paymentDTO);

        ResponseEntity<?> response = paymentController.createPayment(paymentDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
        verify(paymentService, times(1)).createPayment(paymentDTO);
    }

    @Test
    void testUpdatePaymentStatus() {
        String paymentId = "123";
        PaymentDTO paymentDTO = new PaymentDTO();
        doNothing().when(paymentService).updatePayment(paymentId, paymentDTO);

        ResponseEntity<?> response = paymentController.uptadeStatus(paymentId, paymentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode()); 
        verify(paymentService, times(1)).updatePayment(paymentId, paymentDTO);
    }

    @Test
    void testGetPayments() {
        List<PaymentDTO> payments = Arrays.asList(new PaymentDTO(), new PaymentDTO());
        when(paymentService.getAllPayments()).thenReturn(payments);

        ResponseEntity<?> response = paymentController.getPayments();

        assertEquals(HttpStatus.OK, response.getStatusCode()); // HTTP Status OK
        assertEquals(payments, response.getBody());
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void testGetPaymentByIdFound() {
        String paymentId = "123";
        PaymentDTO paymentDTO = new PaymentDTO();
        when(paymentService.getById(paymentId)).thenReturn(paymentDTO);

        ResponseEntity<?> response = paymentController.getById(paymentId);

        assertEquals(HttpStatus.OK, response.getStatusCode()); 
        assertEquals(paymentDTO, response.getBody());
        verify(paymentService, times(1)).getById(paymentId);
    }

    @Test
    void testGetPaymentByIdNotFound() {
        String paymentId = "123";
        when(paymentService.getById(paymentId)).thenReturn(null);

        ResponseEntity<?> response = paymentController.getById(paymentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
        assertNull(response.getBody());
        verify(paymentService, times(1)).getById(paymentId);
    }

    @Test
    void testGetPaymentStatusByIdFound() {
        String paymentId = "123";
        String status = "Completed";
        when(paymentService.getEstatusById(paymentId)).thenReturn(status);

        ResponseEntity<?> response = paymentController.getEstatusById(paymentId);

        assertEquals(HttpStatus.OK, response.getStatusCode()); // HTTP Status OK
        assertEquals(status, response.getBody());
        verify(paymentService, times(1)).getEstatusById(paymentId);
    }

    
	@Test
    void testGetPaymentStatusByIdNotFound() {

        String paymentId = "123";
        when(paymentService.getEstatusById(paymentId)).thenReturn(null);

        ResponseEntity<?> response = paymentController.getEstatusById(paymentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // HTTP Status NOT FOUND
        assertNull(response.getBody());
        verify(paymentService, times(1)).getEstatusById(paymentId);
    }
    
    @Test
    void testGetPaymentsByIssuer() {
        String issuer = "User123";
        List<PaymentDTO> payments = Arrays.asList(new PaymentDTO(), new PaymentDTO());
        when(paymentService.getPaymentsByEmisor(issuer)).thenReturn(payments);

        ResponseEntity<?> response = paymentController.getPaymentsByEmisorPago(issuer);

        assertEquals(HttpStatus.OK, response.getStatusCode()); // HTTP Status OK
        assertEquals(payments, response.getBody());
        verify(paymentService, times(1)).getPaymentsByEmisor(issuer);
    }
}