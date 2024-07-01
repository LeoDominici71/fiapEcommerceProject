package com.fiap.shooping_cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.shooping_cart.entities.Products;
import com.fiap.shooping_cart.entities.ProductsRequest;
import com.fiap.shooping_cart.entities.ShoopingCart;
import com.fiap.shooping_cart.factory.Factory;
import com.fiap.shooping_cart.repository.ShoopingCartRepository;
import com.fiap.shooping_cart.service.IntegrationService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ShoopingCartRepository repository;

	@MockBean
	private IntegrationService service;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveRegistrarUmaCompraNoCarrinho() throws Exception {
		// Arrange
		ProductsRequest request = Factory.createShoopingCartRequest();
		String jsonBody = objectMapper.writeValueAsString(request);

		Products product = Factory.createProduct();

		Mockito.when(service.getProduct(request.getId(), request.getQuantity(), "34234342423423542523525"))
				.thenReturn(product);

		// Act
		ResultActions response = mockMvc
				.perform(post("/api/cart/add").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.header("id", "1").header("authorization", "34234342423423542523525"));

		response.andExpect(status().isOk());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void naoDeveRegistrarUmaCompraNoCarrinho() throws Exception {
		// Arrange
		ProductsRequest request = Factory.createShoopingCartRequest();
		String jsonBody = objectMapper.writeValueAsString(request);

		// Act
		ResultActions response = mockMvc
				.perform(post("/api/cart/add").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.header("id", "1").header("authorization", "34234342423423542523525"));

		response.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveRegistrarUmaCompraNoCarrinhoQuandoJaPossuirItensLa() throws Exception {
		// Arrange
		ProductsRequest request = Factory.createShoopingCartRequest();
		String jsonBody = objectMapper.writeValueAsString(request);

		ShoopingCart cart = Factory.createShoopingCart();

		repository.save(cart);
		
		Products product = Factory.createProduct();

		Mockito.when(service.getProduct(request.getId(), request.getQuantity(), "34234342423423542523525"))
				.thenReturn(product);

		// Act
		ResultActions response = mockMvc
				.perform(post("/api/cart/add").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.header("id", "1").header("authorization", "34234342423423542523525"));

		response.andExpect(status().isOk());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveDeletarItemDoCarrinhoPorId() throws Exception {
		ProductsRequest request = Factory.createShoopingCartRequest();
		String jsonBody = objectMapper.writeValueAsString(request);

		Products product = Factory.createProduct();
		ShoopingCart cart = Factory.createShoopingCart();

		repository.save(cart);
		// Act
		Mockito.when(service.rollbackProduct(request.getId(), request.getQuantity(), "34234342423423542523525"))
				.thenReturn(product);

		ResultActions response = mockMvc
				.perform(delete("/api/cart/remove").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.header("id", "1").header("authorization", "34234342423423542523525"));

		// Assert
		response.andExpect(status().isNoContent());

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveDeletarCarrinhoPorId() throws Exception {

		ShoopingCart cart = Factory.createShoopingCart();

		repository.save(cart);
		// Act

		ResultActions response = mockMvc.perform(
				delete("/api/cart/remove-cart").header("id", "1").header("authorization", "34234342423423542523525"));

		// Assert
		response.andExpect(status().isNoContent());

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveBuscarCarrinhoPorId() throws Exception {
		ShoopingCart cart = Factory.createShoopingCart();

		// Arrange
		repository.save(cart);

		// Act
		ResultActions response = mockMvc.perform(
				get("/api/cart/get-cart-by-user").header("id", "1").header("authorization", "34234342423423542523525"));

		// Assert
		response.andExpect(status().isOk());

	}
}
