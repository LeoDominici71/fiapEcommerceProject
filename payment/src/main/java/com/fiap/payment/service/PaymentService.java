package com.fiap.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.payment.entities.Payment;
import com.fiap.payment.entities.PaymentRequest;
import com.fiap.payment.entities.ShoopingCart;
import com.fiap.payment.exception.GeneralClientSystemException;
import com.fiap.payment.repository.PaymentRepository;

@Service
public class PaymentService {
	
	
	@Autowired
	private PaymentRepository repository;
	
	@Autowired
	private PaymentCollectorService validate;
	
	@Autowired
	private AsyncDeleteCart delete;
	
	public Payment registerPayment(PaymentRequest request, Long id, String authorization) {
		try {
			ShoopingCart shoopingCart = validate.validateCard(request.getCardNumber(), authorization);
			delete.deleteCart(authorization);
		    return repository.save(new Payment(request, id, shoopingCart));

		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralClientSystemException(e.getMessage());

		}
		
	} 
	
	public List<Payment> getPaymentList(Long id){
		return repository.findByUserId(id);
	}

}
