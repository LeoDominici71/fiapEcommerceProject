package com.fiap.payment.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
	
	private String cardNumber;
    private String cardHolderName;
    private String cvv;
    private String cardHolderDocument;
    private String tipoPagamento;
}
