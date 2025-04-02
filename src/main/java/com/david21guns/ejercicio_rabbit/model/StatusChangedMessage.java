package com.david21guns.ejercicio_rabbit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusChangedMessage {
	private String id;
	private String status;
}
