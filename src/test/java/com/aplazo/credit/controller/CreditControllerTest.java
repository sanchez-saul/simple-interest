package com.aplazo.credit.controller;

import com.aplazo.credit.dto.CreditDTO;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditControllerTest {

    private static final String API_PATH = "/api/credits";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void testCreateCredit() throws Exception {
        CreditDTO credit = createCreditObject();
        credit.setTerms(5);
        mvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(credit)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void testRequestCredit() throws Exception {
        CreditDTO credit = createCreditObject();
        mvc.perform(post(API_PATH+ "/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(credit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(equalTo(4))));
    }

    @Test
    @Order(3)
    public void testUpdateCredit() throws Exception {
        CreditDTO credit = createCreditObject();
        credit.setCreditId(1);
        credit.setRate(20.0);
        mvc.perform(put(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(credit)))
                .andExpect(status().isOk());

    }

    @Test
    @Order(4)
    public void testGetCredits() throws Exception {
        mvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @Order(5)
    public void testGetCreditById() throws Exception {
        mvc.perform(get(API_PATH+ "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creditId", is(1)));
    }

    @Test
    @Order(6)
    public void testDeleteCredit() throws Exception {
        mvc.perform(delete(API_PATH+ "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(7)
    public void testDeleteCreditNotFound() throws Exception {
        mvc.perform(delete(API_PATH + "/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    public void testUpdateCreditNotFound() throws Exception {
        CreditDTO credit = new CreditDTO();
        credit.setCreditId(100);
        mvc.perform(put(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(credit)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    public void testGetCreditByIdNotFound() throws Exception {
        mvc.perform(get(API_PATH+ "/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(10)
    public void testCreateCreditBadRequest() throws Exception {
        CreditDTO credit = createCreditObject();
        credit.setTerms(1);
        mvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(credit)))
                .andExpect(status().isBadRequest());
    }

    private CreditDTO createCreditObject() {
        CreditDTO credit = new CreditDTO();
        credit.setTerms(4);
        credit.setAmount(10001.0);
        credit.setRate(10.0);
        return credit;
    }

}
