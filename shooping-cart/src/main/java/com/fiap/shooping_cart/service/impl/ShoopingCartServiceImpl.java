package com.fiap.shooping_cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fiap.shooping_cart.entities.Products;
import com.fiap.shooping_cart.entities.ProductsRequest;
import com.fiap.shooping_cart.entities.ShoopingCart;
import com.fiap.shooping_cart.exception.GeneralClientSystemException;
import com.fiap.shooping_cart.repository.ProductsRepository;
import com.fiap.shooping_cart.repository.ShoopingCartRepository;
import com.fiap.shooping_cart.service.IntegrationService;
import com.fiap.shooping_cart.service.ShoopingCartService;
import com.fiap.shooping_cart.utils.ApplicationUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoopingCartServiceImpl implements ShoopingCartService {

	private final IntegrationService integration;
	private final ShoopingCartRepository repository;

	public ShoopingCartServiceImpl(ShoopingCartRepository repository, IntegrationService integration) {
		this.repository = repository;
		this.integration = integration;
	}

	@Override
	public ShoopingCart getCartByUserId(Long id) {
		// TODO Auto-generated method stub
		ShoopingCartServiceImpl.log.info("IN - Get cart by user id");

		try {
			Optional<ShoopingCart> request = repository.findByClientId(id);
			ShoopingCart cart = request.orElseThrow(() -> new EntityNotFoundException("No products on cart"));
			return cart;
		} catch (Exception e) {
			throw new GeneralClientSystemException("No products on cart");
		}
	}

	@Override
	public ShoopingCart addProductOnCart(Long id, ProductsRequest products, String authorization) {
		ShoopingCartServiceImpl.log.info("IN - Update cart products by Id");

		try {
			ShoopingCart cartSaved;
			Products productResponse = integration.getProduct(products.getId(), products.getQuantity(), authorization);
			productResponse.setQuantity(products.getQuantity());
			Optional<ShoopingCart> request = repository.findByClientId(id);
			if (!request.isPresent()) {
				ShoopingCart newCart = new ShoopingCart();
				newCart.setClientId(id);
				newCart.addProduct(productResponse);
				newCart.setPriceTotal(ApplicationUtils.priceUpdated(newCart));

				cartSaved = repository.save(newCart);

			} else {

				cartSaved = repository.save(ApplicationUtils.addToShoopingCart(request, productResponse));
			}
			return cartSaved;

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Error in updating cart");
		}
	}

	@Override
	public ShoopingCart deleteProduct(Long id, ProductsRequest products, String authorization) {

		try {
			Products product = integration.rollbackProduct(products.getId(), products.getQuantity(), authorization);
			product.setQuantity(products.getQuantity());
			Optional<ShoopingCart> request = repository.findByClientId(id);
			ShoopingCart cart = request.orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));

			ShoopingCart cartSaved = repository.save(ApplicationUtils.removeProductShoopingCart(cart, product));
			return cartSaved;

		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralClientSystemException("No products on cart");
		}

	}

	@Override
	public void deleteByUserId(Long id) {

		try {
			
			Optional<ShoopingCart> request = repository.findByClientId(id);
			ShoopingCart cart = request.orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));
			repository.delete(cart);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralClientSystemException("Error while delete cart");
		}
	}

}
