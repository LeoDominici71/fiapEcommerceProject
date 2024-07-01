package com.fiap.shooping_cart.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fiap.shooping_cart.entities.ProductResponse;
import com.fiap.shooping_cart.entities.Products;
import com.fiap.shooping_cart.entities.ShoopingCart;

import jakarta.persistence.EntityNotFoundException;

public class ApplicationUtils {

	

	public static ShoopingCart toShoppingCart(List<ProductResponse> products) {
		ShoopingCart cart = new ShoopingCart();

		List<Products> convertedProducts = new ArrayList<>();
		for (ProductResponse productResponse : products) {
			Products product = new Products();
			product.setId(productResponse.getId());
			product.setName(productResponse.getName());
			product.setDescription(productResponse.getDescription());
			product.setStock(productResponse.getStock());
			product.setPrice(productResponse.getPrice());
			convertedProducts.add(product);
		}

		Double totalPrice = convertedProducts.stream().mapToDouble(Products::getPrice).sum();
		cart.setPriceTotal(totalPrice);

		cart.setProducts(convertedProducts);

		return cart;

	}

	public static Products toProducts(ProductResponse productResponse) {
		Products product = new Products();
		product.setId(productResponse.getId());
		product.setName(productResponse.getName());
		product.setDescription(productResponse.getDescription());
		product.setStock(productResponse.getStock());
		product.setPrice(productResponse.getPrice());

		return product;
	}

	public static ShoopingCart addToShoopingCart(Optional<ShoopingCart> newCart, Products newProduct) {
		ShoopingCart cart = newCart.orElseThrow(() -> new EntityNotFoundException("Cart not found"));
		if (cart.getProducts().contains(newProduct)) {
			Integer index = cart.getProducts().indexOf(newProduct);
			Products productOld = cart.getProducts().get(index);

			Integer oldValue = productOld.getQuantity();
			Integer newValue = newProduct.getQuantity();
			Integer updatedValue = oldValue + newValue;
			productOld.setQuantity(updatedValue); 
			// update price
			cart.setPriceTotal(priceUpdated(cart));

		} else {
			cart.getProducts().add(newProduct);
		}
		return cart;
	}

	public static ShoopingCart removeProductShoopingCart(ShoopingCart cart, Products newProduct) {

		if (cart.getProducts().contains(newProduct)) {
			Integer index = cart.getProducts().indexOf(newProduct);
			Products productOld = cart.getProducts().get(index);
			if(productOld.getQuantity() == newProduct.getQuantity()) {
			cart.getProducts().remove(cart.getProducts().indexOf(newProduct));
			}else {
				productOld.setQuantity(productOld.getQuantity() - newProduct.getQuantity());
			}
			cart.setPriceTotal(priceUpdated(cart));

		}

		return cart;

	}

	public static Double priceUpdated(ShoopingCart newCart) {
		return newCart.getProducts().stream()
				.mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
	}

	

}
