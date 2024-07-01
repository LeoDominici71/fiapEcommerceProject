package com.fiap.payment.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "tb_payments")
@NoArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String cardNumber;
    private String cardHolderName;
    private String cvv;
    private String cardHolderDocument;
    private String tipoPagamento;
    private Double valorTotal;
    private Long userId;
    
    public Payment(PaymentRequest request, Long id, ShoopingCart shoopingCart) {
    	setCardHolderDocument(request.getCardHolderDocument());
    	setCardHolderName(request.getCardHolderName());
    	setCardNumber(request.getCardNumber());
    	setCvv(request.getCvv());
    	setValorTotal(shoopingCart.getPriceTotal());
    	setUserId(id);
    	setTipoPagamento(request.getTipoPagamento());
    }
}
