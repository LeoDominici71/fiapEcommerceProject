package com.fiap.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiap.payment.entities.ShoopingCart;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentCollectorService {

	@Autowired
	private RestTemplate restTemplate;

	public ShoopingCart validateCard(String cardNumber, String authorization) throws Exception {
		if (cardNumber.length() < 13 || cardNumber.length() > 16) {
			throw new Exception("card not valid");
		}

		HttpHeaders getHeaders = new HttpHeaders();
		getHeaders.setBearerAuth(authorization);
		HttpEntity<Void> getRequestEntity = new HttpEntity<>(getHeaders);

		String urlGet = "http://localhost:8765/shoppingCart/api/cart/get-cart-by-user";
		ResponseEntity<ShoopingCart> shoopingCart = restTemplate.exchange(urlGet, HttpMethod.GET,
				getRequestEntity, ShoopingCart.class);
		
		PaymentCollectorService.log.info("OUT - getting Shooping cart application");

		return shoopingCart.getBody();
		

	}

}
