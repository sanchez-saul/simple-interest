package com.aplazo.credit.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class CreditDTO {

	private Integer creditId;

	@NotNull(message = "{terms.mandatory}")
	@Min(value = 4, message = "{terms.range}")
	@Max(value = 52, message = "{terms.range}")
	private Integer terms;

	@NotNull(message = "{amount.mandatory}")
	@DecimalMin(value = "1.00", message = "{amount.range}")
	@DecimalMax(value = "999999.00", message = "{amount.range}")
	private Double amount;

	@NotNull(message = "{rate.mandatory}")
	@DecimalMin(value = "1", message = "{rate.range}")
	@DecimalMax(value = "100", message = "{rate.range}")
	private Double rate;

}
