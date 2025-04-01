package com.david21guns.ejercicio_rabbit.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david21guns.ejercicio_rabbit.config.RabbitMQConfig;
import com.david21guns.ejercicio_rabbit.exception.ResourceNotFoundException;
import com.david21guns.ejercicio_rabbit.model.Payment;
import com.david21guns.ejercicio_rabbit.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "payments")
public class PaymentController {

	private final RabbitTemplate template;

	private final PaymentRepository repository;
	
	
	@PostMapping(path = "/payment")
	public ResponseEntity<Payment> createPayment( @RequestBody final Payment payment) {
        return ResponseEntity.ok(repository.save(payment));
	}
	
	@PutMapping(path = "/status/{id}")
	public ResponseEntity<Payment> uptadeStatus(@PathVariable String id, @RequestBody Payment payment) {
		
		Payment updatePayment =repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not exist with id: " + id));
		
		if(updatePayment.getEstatus().compareTo(payment.getEstatus())!=0) {
			// Send message to RabbitMQ if new status is different from current status
			StringBuilder sb = new StringBuilder("Status changed: ");
			sb.append(id);
			
			this.template.convertAndSend(RabbitMQConfig.EXCHANGE, "", sb.toString());
		}
		updatePayment.setEstatus(payment.getEstatus());
		repository.save(updatePayment);
        return ResponseEntity.ok(updatePayment);
		
	}
}
