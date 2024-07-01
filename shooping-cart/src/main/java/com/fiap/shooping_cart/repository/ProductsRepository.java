package com.fiap.shooping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.shooping_cart.entities.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>{
	

}
