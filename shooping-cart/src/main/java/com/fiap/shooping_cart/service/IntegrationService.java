package com.fiap.shooping_cart.service;

import org.springframework.stereotype.Service;

import com.fiap.shooping_cart.entities.Products;

@Service
public interface IntegrationService {

	Products getProduct(Long productId, Integer quantity, String authorization);

	Products rollbackProduct(Long id, Integer quantity, String authorization);

}
