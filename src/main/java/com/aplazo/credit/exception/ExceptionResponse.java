package com.aplazo.credit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

	private LocalDateTime dateTime;
	private String message;
	private String detail;

}
