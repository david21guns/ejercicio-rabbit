package com.david21guns.ejercicio_rabbit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import com.david21guns.ejercicio_rabbit.dto.PaymentDTO;
import com.david21guns.ejercicio_rabbit.exception.ResourceNotFoundException;
import com.david21guns.ejercicio_rabbit.model.Payment;
import com.david21guns.ejercicio_rabbit.repository.PaymentRepository;

@SpringBootTest
class PaymentControllerTest {
	
	@InjectMocks
	private PaymentController controller;
	
	@Mock
	private RabbitTemplate template;

	@Mock
	private PaymentRepository repository;
	
	private Payment mockPayment;
	
    @BeforeEach
    public void setup(){
	  this.mockPayment = new Payment("67eb0a67bd5d3e1e163abd32","concepto",1,"emisor","receptor",10.50,"estatus","2025-04-01");
	  
    }
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	/*
	@Test
	void testCreatePayment() {
		 PaymentDTO mockPaymentDTO = new PaymentDTO("","",1,"","",2.2,"","");
		assertThat(this.controller.createPayment(this.mockPayment)).isNotNull();
	}
	*/
	
   /*
    * @Test
    void testExceptionIsThrown() {
        assertThrows(ResourceNotFoundException.class, () -> {
            throwException();
        });
    }

    private void throwException() {
        //controller.uptadeStatus("321", this.mockPayment);
    }
    */
}
