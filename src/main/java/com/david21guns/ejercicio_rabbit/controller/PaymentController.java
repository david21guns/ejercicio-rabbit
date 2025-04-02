package com.david21guns.ejercicio_rabbit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david21guns.ejercicio_rabbit.dto.PaymentDTO;
import com.david21guns.ejercicio_rabbit.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PaymentController {
	
	private final PaymentService paymentService;

	@PostMapping(path = "/payment")
	public ResponseEntity<?> createPayment(@RequestBody final PaymentDTO payment) {
		paymentService.createPayment(payment);
        return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/payment/{id}")
	public ResponseEntity<?> uptadeStatus(@PathVariable String id, @RequestBody PaymentDTO payment) {
		paymentService.updatePayment(id, payment);
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path = "/payments")
	public ResponseEntity<?> getPayments(){
		List<PaymentDTO> response = paymentService.getAllPayments();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(path = "/payment/{id}")
	public ResponseEntity<?> getById(@PathVariable String id){
		PaymentDTO response = paymentService.getById(id);
		if(null==response) {
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	
	@GetMapping(path = "/payment/{id}/status")
	public ResponseEntity<?> getEstatusById(@PathVariable String id){
		String response = paymentService.getEstatusById(id);
		if(null==response) {
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	
	@GetMapping(path = "/payments/issuer/{issuer}")
	public ResponseEntity<?> getPaymentsByEmisorPago(@PathVariable String issuer){
		List<PaymentDTO> response = paymentService.getPaymentsByEmisor(issuer);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
