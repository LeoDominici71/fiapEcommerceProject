package com.fiap.payment.factory;

import java.util.ArrayList;
import java.util.List;

import com.fiap.payment.entities.Payment;
import com.fiap.payment.entities.PaymentRequest;
import com.fiap.payment.entities.Products;
import com.fiap.payment.entities.ShoopingCart;


public class Factory {
	
	public static PaymentRequest createPaymentRequest() {
		PaymentRequest request = new PaymentRequest();
		request.setCardHolderDocument("3423442424");
		request.setCardHolderName("Osvald");
		request.setCardNumber("12345678901234");
		request.setCvv("322");
		request.setTipoPagamento("Credit");
		
		return request;
		
	}
	
	public static Payment createPayment() {
		Payment request = new Payment();
		request.setCardHolderDocument("3423442424");
		request.setCardHolderName("Osvald");
		request.setCardNumber("12345678901234");
		request.setCvv("322");
		request.setTipoPagamento("Credit");
		request.setId(1L);
		request.setUserId(1L);
		request.setValorTotal(23.4);
		
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
