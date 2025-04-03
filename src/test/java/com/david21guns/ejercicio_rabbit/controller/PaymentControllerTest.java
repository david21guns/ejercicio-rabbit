package com.david21guns.ejercicio_rabbit.controller;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.david21guns.ejercicio_rabbit.dto.PaymentDTO;
import com.david21guns.ejercicio_rabbit.service.PaymentService;

@SpringBootTest
class PaymentControllerTest {
	
	@InjectMocks
	private PaymentController controller;
	
	@Mock
	private PaymentService paymentService;
		
    @BeforeEach
    public void setup(){
    	this.controller = new PaymentController(paymentService);
    }
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	
	@Test
	void testCreatePayment() {
		 PaymentDTO mockPaymentDTO = new PaymentDTO("","",1,"","",2.2,"","");
		assertThat(this.controller.createPayment(mockPaymentDTO)).isNotNull();
	}
	
	
   /*
    @Test
    void testExceptionIsThrown() {
        assertThrows(ResourceNotFoundException.class, () -> {
            throwException();
        });
    }

    private void throwException() {
		 PaymentDTO mockPaymentDTO = new PaymentDTO("","",1,"","",2.2,"","");

        controller.uptadeStatus("321",mockPaymentDTO );
    }
    */
	/*
	@Test
	void testGetAll() {
		List<PaymentDTO> list = new ArrayList<PaymentDTO>();
		ResponseEntity<?> responseEntity = new ResponseEntity<>(list,HttpStatus.OK);
		
		when(controller.getPayments()).thenReturn(responseEntity);
		
		ResponseEntity<?> response=controller.getPayments();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}*/
}
