package com.aplazo.credit.repo;

import com.aplazo.credit.model.Credit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.AssertionErrors;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditRepoTest {

    @Autowired
    private ICreditRepo repo;

    @Test
    @Order(1)
    @Rollback(value = false)
    void testCreateCredit() {
        Credit credit = new Credit();
        credit.setTerms(6);
        credit.setAmount(10000.0);
        credit.setRate(10.0);

        repo.save(credit);
        Assertions.assertThat(credit.getCreditId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void testGetCredits() {
        List<Credit> list = repo.findAll();
        Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void testGetCredit() {
        Optional<Credit> optionalCredit = repo.findById(1);
        assert (optionalCredit.isPresent());
    }


}
