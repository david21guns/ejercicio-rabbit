package com.david21guns.ejercicio_rabbit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO{
	@JsonIgnore
	private String id;
	private  String concepto;
	private  int cantidadProductos;
	private  String emisorPago;
	private  String receptorPago;
	private  double montoTotal;
	private  String estatus;
	private  String fechaPago;
}
