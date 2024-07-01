package com.fiap.shooping_cart.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiap.shooping_cart.entities.ProductResponse;
import com.fiap.shooping_cart.entities.ProductUpdateStockRequest;
import com.fiap.shooping_cart.entities.Products;
import com.fiap.shooping_cart.exception.GeneralClientSystemException;
import com.fiap.shooping_cart.service.IntegrationService;
import com.fiap.shooping_cart.utils.ApplicationUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntegrationServiceImpl implements IntegrationService {

	@Autowired
	private RestTemplate restTemplate;


	@Override
	public Products getProduct(Long productId, Integer quantity, String authorization) {

		try {
			String urlGet = "http://localhost:8765/productRegistry/api/products/{id}";
			Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put("id", productId.toString());
			IntegrationServiceImpl.log.info("IN - product registry application");

			HttpHeaders getHeaders = new HttpHeaders();
			getHeaders.setBearerAuth(authorization);
			HttpEntity<Void> getRequestEntity = new HttpEntity<>(getHeaders);

			ResponseEntity<ProductResponse> productResponse = restTemplate.exchange(urlGet, HttpMethod.GET,
					getRequestEntity, ProductResponse.class, uriVariables);
			IntegrationServiceImpl.log.info("OUT - product registry application");

			String urlPut = "http://localhost:8765/productRegistry/api/products/update/stock/" + productId;

			ProductUpdateStockRequest request = new ProductUpdateStockRequest();
			Integer stockUpdated = Integer.parseInt(productResponse.getBody().getStock()) - quantity;
			request.setStock(stockUpdated.toString());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(authorization);

			HttpEntity<ProductUpdateStockRequest> requestEntity = new HttpEntity<>(request, headers);
			IntegrationServiceImpl.log.info("IN - product registry update application");

			ResponseEntity<ProductResponse> response = restTemplate.exchange(urlPut, HttpMethod.PUT, requestEntity,
					ProductResponse.class);
			IntegrationServiceImpl.log.info("OUT - product stock updated with status {} ",
					response.getStatusCode().toString());

			return ApplicationUtils.toProducts(productResponse.getBody());

		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralClientSystemException("Error in updating stock");
		}
	}

	@Override
	public Products rollbackProduct(Long id, Integer quantity, String authorization) {
		// TODO Auto-generated method stub

		try {
			String urlGet = "http://localhost:8765/productRegistry/api/products/{id}";

			Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put("id", id.toString());
			IntegrationServiceImpl.log.info("IN - product registry application");

			HttpHeaders getHeaders = new HttpHeaders();
			getHeaders.setBearerAuth(authorization);
			HttpEntity<Void> getRequestEntity = new HttpEntity<>(getHeaders);

			ResponseEntity<ProductResponse> productResponse = restTemplate.exchange(urlGet, HttpMethod.GET,
					getRequestEntity, ProductResponse.class, uriVariables);
			IntegrationServiceImpl.log.info("OUT - product registry application");

			String urlPut = "http://localhost:8765/productRegistry/api/products/update/stock/" + id;

			ProductUpdateStockRequest request = new ProductUpdateStockRequest();
			Integer stockUpdated = Integer.parseInt(productResponse.getBody().getStock()) + quantity;
			request.setStock(stockUpdated.toString());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(authorization);

			HttpEntity<ProductUpdateStockRequest> requestEntity = new HttpEntity<>(request, headers);
			IntegrationServiceImpl.log.info("IN - product registry update application");

			ResponseEntity<ProductResponse> response = restTemplate.exchange(urlPut, HttpMethod.PUT, requestEntity,
					ProductResponse.class);
			IntegrationServiceImpl.log.info("OUT - product stock updated with status {} ",
					response.getStatusCode().toString());

			return ApplicationUtils.toProducts(response.getBody());

		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralClientSystemException("Error in updating stock");

		}

	}

}
