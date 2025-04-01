package com.david21guns.ejercicio_rabbit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("payment")
public class Payment {
	@Id
	private String id;
	@Field("concepto")
	private String concepto;
	@Field("cantidadProductos")
	private int cantidadProductos;
	@Field("emisorPago")
	private String emisorPago;
	@Field("receptorPago")
	private String receptorPago;
	@Field("montoTotal")
	private double montoTotal;
	@Field("estatus")
	private String estatus;
	@Field("fechaPago")
	private String fechaPago;
}
