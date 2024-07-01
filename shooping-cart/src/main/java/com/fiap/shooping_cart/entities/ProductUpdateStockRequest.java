package com.fiap.shooping_cart.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateStockRequest {
	
	@NotNull
	private String stock;

}
