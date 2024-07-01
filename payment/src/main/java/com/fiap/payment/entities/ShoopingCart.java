package com.fiap.payment.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShoopingCart {
	
	
	private Long id;
	private Long clientId;
	private Double priceTotal;
	private List<Products> products = new ArrayList<>();
    
    public void addProduct(Products product) {
    	products.add(product);
    	product.setCart(this);
    }
    
    public void removeProduct(Products product) {
    	products.remove(product);
    	product.setCart(null);
    }

}
