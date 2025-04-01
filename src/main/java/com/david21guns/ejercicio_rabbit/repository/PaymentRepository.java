package com.david21guns.ejercicio_rabbit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.david21guns.ejercicio_rabbit.model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String>{
	
}
