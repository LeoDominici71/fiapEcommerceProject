package com.fiap.shooping_cart.factory;

import java.util.ArrayList;
import java.util.List;

import com.fiap.shooping_cart.entities.Products;
import com.fiap.shooping_cart.entities.ProductsRequest;
import com.fiap.shooping_cart.entities.ShoopingCart;

public class Factory {
	
	public static ProductsRequest createShoopingCartRequest() {
		ProductsRequest request = new ProductsRequest();
		request.setId(1L);
		request.setQuantity(1);
		
		return request;
	}
	
	public static Products createProduct() {
		Products products = new Products();
		products.setId(1L);
		products.setName("Leonardo");
		products.setPrice(23.0);
		products.setQuantity(1);
		products.setStock("20");
		return products;
	}
	
	public static ShoopingCart createShoopingCart() {
		ShoopingCart shoopingCart = new ShoopingCart();
		List<Products> productList = new ArrayList<>();
		productList.add(Factory.createProduct());
		shoopingCart.setClientId(1L);
		shoopingCart.setId(1L);
		shoopingCart.setPriceTotal(28.9);
		shoopingCart.setProducts(productList);
		
		return shoopingCart;
	}

}
