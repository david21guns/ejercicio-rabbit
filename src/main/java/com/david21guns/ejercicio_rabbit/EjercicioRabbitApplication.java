package com.david21guns.ejercicio_rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.david21guns.ejercicio_rabbit.repository.PaymentRepository;

@EnableMongoRepositories(basePackageClasses = PaymentRepository.class)
@SpringBootApplication
public class EjercicioRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjercicioRabbitApplication.class, args);
	}

}
