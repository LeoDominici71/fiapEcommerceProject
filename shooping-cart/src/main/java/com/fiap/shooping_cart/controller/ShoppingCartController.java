package com.fiap.shooping_cart.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.shooping_cart.entities.ProductsRequest;
import com.fiap.shooping_cart.entities.ShoopingCart;
import com.fiap.shooping_cart.service.ShoopingCartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/cart")
@Slf4j
public class ShoppingCartController {

	private final ShoopingCartService service;

	public ShoppingCartController(ShoopingCartService service) {
		this.service = service;
	}

	@PostMapping(value = "/add")
	public ResponseEntity<ShoopingCart> insertOnCart(@RequestBody ProductsRequest products,
			@RequestHeader Map<String, String> headers) {
		ShoopingCart cart = service.addProductOnCart(Long.parseLong(headers.get("id")), products,
				headers.get("authorization"));
		ShoppingCartController.log.info("OUT - cart insert");
		return ResponseEntity.ok().body(cart);

	}

	@GetMapping(value = "/get-cart-by-user")
	public ResponseEntity<ShoopingCart> getCartByUserId(@RequestHeader Map<String, String> headers) {
		ShoopingCart cart = service.getCartByUserId(Long.parseLong(headers.get("id")));
		ShoppingCartController.log.info("OUT - cart by user id");
		return ResponseEntity.ok().body(cart);

	}

	@DeleteMapping(value = "/remove")
	public ResponseEntity<?> deleteOrder(@RequestBody ProductsRequest products ,@RequestHeader Map<String, String> headers) {
		service.deleteProduct(Long.parseLong(headers.get("id")), products, headers.get("authorization"));
		ShoppingCartController.log.info("OUT - DeleteOrder");
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/remove-cart")
	public ResponseEntity<Void> deleteCartByUserId(@RequestHeader Map<String, String> headers) {
		service.deleteByUserId(Long.parseLong(headers.get("id")));
		return ResponseEntity.noContent().build();
	}

}
