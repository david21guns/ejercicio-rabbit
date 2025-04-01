package com.david21guns.ejercicio_rabbit.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String EXCHANGE="test.fanout";

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate amqptemplate(ConnectionFactory connection) {
		final var template= new RabbitTemplate(connection);
		template.setMessageConverter(messageConverter());
		template.setExchange(ExchangeTypes.FANOUT);
		return template;
	}
	
	@Bean
	public FanoutExchange fanout() {
		return new FanoutExchange(EXCHANGE);
	}
	
	@Bean
	public Binding binding1(FanoutExchange fanout,
	        Queue autoDeleteQueue1) {
		return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
	}
	
	@Bean
	public Queue autoDeleteQueue1() {
		return new AnonymousQueue();
	}

}
