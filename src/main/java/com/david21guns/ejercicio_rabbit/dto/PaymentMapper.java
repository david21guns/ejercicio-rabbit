package com.david21guns.ejercicio_rabbit.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.david21guns.ejercicio_rabbit.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

	PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);
	
	 PaymentDTO paymentToPaymentDto(Payment payment);
	 
	 Payment paymentDtoToPayment(PaymentDTO paymentDTO);
}
