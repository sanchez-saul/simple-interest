package com.aplazo.credit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentResponseDTO {

    private Integer paymentNumber;
    private Double amount;
    private LocalDate paymentDate;

}
