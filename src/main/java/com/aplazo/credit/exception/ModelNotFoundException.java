package com.aplazo.credit.exception;

public class ModelNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ModelNotFoundException(String msg) {
		super(msg);
	}
}
