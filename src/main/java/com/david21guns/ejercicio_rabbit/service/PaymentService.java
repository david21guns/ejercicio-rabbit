package com.david21guns.ejercicio_rabbit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.david21guns.ejercicio_rabbit.config.RabbitMQConfig;
import com.david21guns.ejercicio_rabbit.dto.PaymentDTO;
import com.david21guns.ejercicio_rabbit.dto.PaymentMapper;
import com.david21guns.ejercicio_rabbit.exception.ResourceNotFoundException;
import com.david21guns.ejercicio_rabbit.model.Payment;
import com.david21guns.ejercicio_rabbit.model.StatusChangedMessage;
import com.david21guns.ejercicio_rabbit.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository repository;
	
	private final RabbitTemplate template;

	public void createPayment(PaymentDTO paymentDTO) {
        Payment payment = PaymentMapper.mapper.paymentDtoToPayment(paymentDTO);
        repository.save(payment);
	}
	
	public void updatePayment(String id, PaymentDTO payment) {
		Payment updatePayment =repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not exist with id: " + id));

		if(updatePayment.getEstatus().compareTo(payment.getEstatus())!=0) {
			updatePayment.setEstatus(payment.getEstatus());
			
			// Send message to RabbitMQ if new status is different from current status
			StatusChangedMessage message= new StatusChangedMessage(id, payment.getEstatus());
			this.template.convertAndSend(RabbitMQConfig.EXCHANGE, "", message);
		}
		repository.save(updatePayment);
	}
	
	public List<PaymentDTO> getAllPayments(){
		List<Payment>  payments = repository.findAll();
        List<PaymentDTO> paymentDTOS = payments.stream().map(
                payment -> PaymentMapper.mapper.paymentToPaymentDto(payment)).collect(Collectors.toList());
		return paymentDTOS;
	}
	
	public PaymentDTO getById(String id) {
		Optional<Payment> payment = Optional.ofNullable(repository.findById(id).orElse(null));
		
        if (payment.isPresent()){
        	 PaymentDTO response =   PaymentMapper.mapper.paymentToPaymentDto(payment.get());
        	 return response;
        }
       
	    return null;
	}
	
	public String getEstatusById(String id) {
		Optional<Payment> payment = Optional.ofNullable(repository.findById(id).orElse(null));
		
        if (payment.isPresent()){
        	 PaymentDTO response =   PaymentMapper.mapper.paymentToPaymentDto(payment.get());
        	 return response.getEstatus();
        }
       
	    return null;
	}
	
	public List<PaymentDTO> getPaymentsByEmisor(String emisorPago){
		List<Payment>  payments = repository.findByEmisorPago(emisorPago);
        List<PaymentDTO> paymentDTOS = payments.stream().map(
                payment -> PaymentMapper.mapper.paymentToPaymentDto(payment)).collect(Collectors.toList());
		return paymentDTOS;
	}
	
	
}
