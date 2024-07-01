package com.fiap.payment.Controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fiap.payment.entities.Payment;
import com.fiap.payment.entities.PaymentRequest;
import com.fiap.payment.service.AsyncDeleteCart;
import com.fiap.payment.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/card")
@Slf4j
public class PaymentController {
	
	@Autowired
	private PaymentService service;
	
	@PostMapping("/validator")
	public ResponseEntity<Payment> registerPayment(@RequestBody PaymentRequest products, @RequestHeader Map<String, String> headers){
		Payment payment = service.registerPayment(products, Long.parseLong(headers.get("id")), headers.get("authorization"));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(headers.get("id"))
				.toUri();
		PaymentController.log.info("OUT - register payment application");

		return ResponseEntity.created(uri).body(payment);
	} 
	
	
	@GetMapping("/get-payment-list")
	public ResponseEntity<List<Payment>> getPaymentById(@RequestHeader Map<String, String> headers){
			return ResponseEntity.ok(service.getPaymentList(Long.parseLong(headers.get("id")))); 
	}

}
