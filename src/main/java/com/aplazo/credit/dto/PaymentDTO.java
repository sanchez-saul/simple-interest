package com.aplazo.credit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDTO {

	private Integer paymentId;
	private CreditDTO credit;
	private Integer paymentNumber;
	private Double amount;
	private LocalDate paymentDate;

}
