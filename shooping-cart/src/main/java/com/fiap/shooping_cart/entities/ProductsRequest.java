package com.fiap.shooping_cart.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductsRequest {
	@NotNull
	private Long id;
	private Integer quantity;

}
