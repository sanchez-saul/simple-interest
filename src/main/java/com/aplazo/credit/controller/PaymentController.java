package com.aplazo.credit.controller;

import com.aplazo.credit.dto.PaymentDTO;
import com.aplazo.credit.exception.ModelNotFoundException;
import com.aplazo.credit.model.Payment;
import com.aplazo.credit.service.IPaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final IPaymentService service;
    private final ModelMapper mapper;
    private final String MSG_PAYMENT_NOT_FOUND;

    public PaymentController(IPaymentService service, ModelMapper mapper, MessageSource msg) {
        this.service = service;
        this.mapper = mapper;
        MSG_PAYMENT_NOT_FOUND = msg.getMessage("payment.notFound", null, LocaleContextHolder.getLocale());
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getPayments() throws Exception {
        List<PaymentDTO> list = service.get().stream()
                .map(p -> mapper.map(p, PaymentDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable("id") Integer id) throws Exception {
        Payment payment = service.getById(id);
        if (payment == null) {
            throwPaymentNotFound(id);
        }
        return new ResponseEntity<>(mapper.map(payment, PaymentDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createPayment(@Valid @RequestBody PaymentDTO dtoRequest) throws Exception {
        Payment payment = service.create(mapper.map(dtoRequest, Payment.class));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(payment.getPaymentId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<PaymentDTO> updatePayment(@RequestBody PaymentDTO dtoRequest) throws Exception {
        Payment payment = service.getById(dtoRequest.getPaymentId());
        if (payment == null) {
            throwPaymentNotFound(dtoRequest.getPaymentId());
        }
        Payment obj = service.update(mapper.map(dtoRequest, Payment.class));
        return new ResponseEntity<>(mapper.map(obj, PaymentDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Integer id) throws Exception {
        Payment payment = service.getById(id);
        if (payment == null) {
            throwPaymentNotFound(id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void throwPaymentNotFound(Integer id) {
        throw new ModelNotFoundException(MSG_PAYMENT_NOT_FOUND + id);
    }
}
