package com.fiap.payment.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.fiap.payment.entities.Payment;
import com.fiap.payment.entities.PaymentRequest;
import com.fiap.payment.entities.ShoopingCart;
import com.fiap.payment.factory.Factory;
import com.fiap.payment.repository.PaymentRepository;
import com.fiap.payment.service.AsyncDeleteCart;
import com.fiap.payment.service.PaymentCollectorService;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PaymentRepository repository;
	
	@MockBean
	private PaymentCollectorService validateCard;
	
	@MockBean
	private AsyncDeleteCart deleteCard;
	
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveRegistrarUmPagamento() throws Exception {
		// Arrange
		PaymentRequest request = Factory.createPaymentRequest();
		String jsonBody = objectMapper.writeValueAsString(request);

        ShoopingCart card = Factory.createShoopingCart();
		
		Mockito.when(validateCard.validateCard(request.getCardNumber(), "34234342423423542523525"))
				.thenReturn(card);
		
		doNothing().when(deleteCard).deleteCart("34234342423423542523525");

		// Act
		ResultActions response = mockMvc
				.perform(post("/api/card/validator").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.header("id", "1").header("authorization", "34234342423423542523525"));

		response.andExpect(status().isCreated());
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void naoDeveRegistrarUmPagamento() throws Exception {
		// Arrange
		PaymentRequest request = Factory.createPaymentRequest();
		String jsonBody = objectMapper.writeValueAsString(request);

		// Act
		ResultActions response = mockMvc
				.perform(post("/api/card/validator").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
						.header("id", "1").header("authorization", "34234342423423542523525"));

		response.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void deveBuscarCarrinhoPorId() throws Exception {
        Payment payment = Factory.createPayment();
		// Arrange
		repository.save(payment);

		// Act
		ResultActions response = mockMvc.perform(
				get("/api/card/get-payment-list").header("id", "1").header("authorization", "34234342423423542523525"));

		// Assert
		response.andExpect(status().isOk());

	}
	
}
