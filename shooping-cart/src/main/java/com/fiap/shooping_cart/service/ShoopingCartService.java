package com.fiap.shooping_cart.service;

import org.springframework.stereotype.Service;

import com.fiap.shooping_cart.entities.ProductsRequest;
import com.fiap.shooping_cart.entities.ShoopingCart;



@Service
public interface ShoopingCartService {


	ShoopingCart getCartByUserId(Long id);

	ShoopingCart addProductOnCart(Long id, ProductsRequest newProduct, String authorization);
	
	ShoopingCart deleteProduct(Long id, ProductsRequest newProduct, String authorization);
	
	void deleteByUserId(Long id);

	

}
