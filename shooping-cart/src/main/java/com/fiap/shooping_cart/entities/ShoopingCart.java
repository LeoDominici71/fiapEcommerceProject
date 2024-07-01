package com.fiap.shooping_cart.entities;

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
@Entity
@Table(name = "tb_cart")
public class ShoopingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    @Column(unique = true, nullable = false)
	private Long clientId;
	private Double priceTotal;
    @OneToMany(mappedBy = "cart", targetEntity = Products.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
