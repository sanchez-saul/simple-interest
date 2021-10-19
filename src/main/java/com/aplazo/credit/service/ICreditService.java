package com.aplazo.credit.service;

import com.aplazo.credit.model.Credit;
import com.aplazo.credit.model.Payment;

import java.util.List;

public interface ICreditService extends ICRUD<Credit, Integer>{

    List<Payment> requestCredit(Credit credit) throws Exception;
}
