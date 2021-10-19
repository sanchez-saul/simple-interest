package com.aplazo.credit.service;

import com.aplazo.credit.model.Credit;
import com.aplazo.credit.model.Payment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditServiceTest {

    @Autowired
    ICreditService service;

    @Test
    @Order(1)
    public void testCreateCredit() throws Exception {
        Credit credit = new Credit();
        credit.setTerms(20);
        credit.setAmount(10000.0);
        credit.setRate(10.0);
        service.create(credit);
        Assertions.assertThat(credit.getCreditId()).isEqualTo(1);
    }

    @Test
    @Order(2)
    public void testUpdateCredit() throws Exception {
        Credit credit = new Credit();
        credit.setCreditId(1);
        credit.setTerms(20);
        credit.setAmount(20000.0);
        credit.setRate(10.0);
        Credit creditUpdated = service.update(credit);
        Assertions.assertThat(creditUpdated.getAmount()).isEqualTo(20000.0);
    }


}
