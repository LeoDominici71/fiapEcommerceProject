package com.fiap.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncDeleteCart {
	
	@Autowired
	private RestTemplate restTemplate;
	
    @Async("taskExecutor")
	public void deleteCart(String authorization) {
		String urlDelete = "http://localhost:8765/shoppingCart/api/cart/remove-cart";

		AsyncDeleteCart.log.info("IN - delete Shooping cart application");

		HttpHeaders getHeaders = new HttpHeaders();
		getHeaders.setBearerAuth(authorization);
		HttpEntity<Void> getRequestEntity = new HttpEntity<>(getHeaders);

        restTemplate.exchange(urlDelete, HttpMethod.DELETE, getRequestEntity, Void.class);
        
        AsyncDeleteCart.log.info("OUT - delete Shooping cart application");
	}

}
