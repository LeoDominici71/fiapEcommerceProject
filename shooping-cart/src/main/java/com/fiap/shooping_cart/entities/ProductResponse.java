package com.fiap.shooping_cart.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
	
	private Long id;
	private String name;
	private String description;
	private String stock;
	private Double price;

}
