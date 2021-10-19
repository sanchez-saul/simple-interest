package com.aplazo.credit.service.impl;

import com.aplazo.credit.model.Credit;
import com.aplazo.credit.model.Payment;
import com.aplazo.credit.repo.IPaymentRepo;
import com.aplazo.credit.repo.IGenericRepo;
import com.aplazo.credit.repo.ICreditRepo;
import com.aplazo.credit.service.ICreditService;

import static com.aplazo.credit.util.InterestUtil.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class CreditServiceImpl extends CRUDImpl<Credit, Integer> implements ICreditService {

    private final ICreditRepo repo;
    private final IPaymentRepo paymentRepo;

    public CreditServiceImpl(ICreditRepo repo, IPaymentRepo paymentRepo){
        this.repo = repo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    protected IGenericRepo<Credit, Integer> getRepo() {
        return repo;
    }

    @Transactional
    @Override
    public List<Payment> requestCredit(Credit credit) throws Exception {
        Double interest = calculateInterest(credit);
        log.info("Interest: " + interest);
        repo.save(credit);
        return calculatePayments(credit, interest);
    }

    private List<Payment> calculatePayments(final Credit credit, Double interest) {
        final Double paymentInterest = calculatePaymentInterest(credit, interest);
        final LocalDate currentDate = LocalDate.now();
        return IntStream.rangeClosed(START_TERM, credit.getTerms())
                .mapToObj(num -> {
                    Payment payment = new Payment();
                    payment.setCredit(credit);
                    payment.setPaymentNumber(num);
                    payment.setAmount(paymentInterest);
                    payment.setPaymentDate(currentDate.plusDays(WEEK * num));
                    paymentRepo.save(payment);
                    return payment;
                }).collect(Collectors.toList());
    }

}
