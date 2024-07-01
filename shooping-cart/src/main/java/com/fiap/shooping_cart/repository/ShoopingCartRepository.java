package com.fiap.shooping_cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.shooping_cart.entities.ShoopingCart;

public interface ShoopingCartRepository extends JpaRepository<ShoopingCart, Long>{
	
	Optional<ShoopingCart> findByClientId(Long clientId);
	

}
