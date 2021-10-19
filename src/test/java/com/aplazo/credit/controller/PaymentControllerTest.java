package com.aplazo.credit.controller;

import com.aplazo.credit.dto.CreditDTO;
import com.aplazo.credit.dto.PaymentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentControllerTest {

    private static final String API_PATH = "/api/payments";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void testCreateCredit() throws Exception {
        CreditDTO credit = new CreditDTO();
        credit.setTerms(10);
        credit.setAmount(10000.0);
        credit.setRate(10.0);
        mvc.perform(post("/api/credits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(credit)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void testCreatePayment() throws Exception {
        mvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createPaymentObject())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void testUpdatePayment() throws Exception {
        PaymentDTO payment = createPaymentObject();
        payment.setPaymentId(1);
        mvc.perform(put(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payment)))
                .andExpect(status().isOk());

    }

    @Test
    @Order(4)
    public void testGetPayments() throws Exception {
        mvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(equalTo(1))));
    }

    @Test
    @Order(5)
    public void testGetPaymentById() throws Exception {
        mvc.perform(get(API_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId", is(1)));
    }

    @Test
    @Order(6)
    public void testDeletePayment() throws Exception {
        mvc.perform(delete(API_PATH+"/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    @Order(7)
    public void testDeletePaymentNotFound() throws Exception {
        mvc.perform(delete(API_PATH+"/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    public void testUpdatePaymentNotFound() throws Exception {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentId(100);
        mvc.perform(put(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payment)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    public void testGetPaymentByIdNotFound() throws Exception {
        mvc.perform(get(API_PATH+"/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(10)
    public void testError500() throws Exception {
        PaymentDTO payment = createPaymentObject();
        payment.getCredit().setCreditId(10);
        mvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payment)))
                .andExpect(status().isInternalServerError());
    }

    private PaymentDTO createPaymentObject() {
        CreditDTO credit = new CreditDTO();
        credit.setCreditId(1);
        PaymentDTO payment = new PaymentDTO();
        payment.setCredit(credit);
        payment.setPaymentNumber(1);
        payment.setAmount(1000.0);
        payment.setPaymentDate(LocalDate.now());
        return payment;
    }

}
