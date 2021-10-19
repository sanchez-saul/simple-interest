package com.aplazo.credit.util;

import com.aplazo.credit.model.Credit;

import java.text.DecimalFormat;

public class InterestUtil {

    private InterestUtil() {
    }

    public static final long WEEK = 7L;
    public static final int START_TERM = 1;

    public static Double calculateInterest(final Credit credit) {
        return credit.getAmount() * (credit.getRate() / 100) * credit.getTerms();
    }

    public static Double calculatePaymentInterest(final Credit credit, final Double interest) {
        return roundAmount((credit.getAmount() + interest) / credit.getTerms());
    }

    public static Double roundAmount(Double amount) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(amount));
    }


}
