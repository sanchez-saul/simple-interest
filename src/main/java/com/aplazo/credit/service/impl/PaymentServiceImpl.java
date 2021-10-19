package com.aplazo.credit.service.impl;

import com.aplazo.credit.model.Payment;
import com.aplazo.credit.repo.IGenericRepo;
import com.aplazo.credit.repo.IPaymentRepo;
import com.aplazo.credit.service.IPaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends CRUDImpl<Payment, Integer> implements IPaymentService {

	private final IPaymentRepo repo;

	public PaymentServiceImpl(IPaymentRepo repo) {
		this.repo = repo;
	}

	@Override
	protected IGenericRepo<Payment, Integer> getRepo() {
		return repo;
	}

}
