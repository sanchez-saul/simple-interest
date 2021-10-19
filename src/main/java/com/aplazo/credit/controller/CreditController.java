package com.aplazo.credit.controller;

import com.aplazo.credit.dto.CreditDTO;
import com.aplazo.credit.dto.PaymentResponseDTO;
import com.aplazo.credit.exception.ModelNotFoundException;
import com.aplazo.credit.model.Credit;
import com.aplazo.credit.service.ICreditService;
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
@RequestMapping("/api/credits")
public class CreditController {

    private final MessageSource msg;
    private final ICreditService service;
    private final ModelMapper mapper;

    public CreditController(ICreditService service, ModelMapper mapper, MessageSource msg) {
        this.service = service;
        this.mapper = mapper;
        this.msg = msg;
    }

    @GetMapping
    public ResponseEntity<List<CreditDTO>> getCredits() throws Exception {
        List<CreditDTO> list = service.get().stream()
                .map(c -> mapper.map(c, CreditDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditDTO> getCreditById(@PathVariable("id") Integer id) throws Exception {
        Credit credit = service.getById(id);
        if (credit == null) {
            throwCreditNotFound(id);
        }
        return new ResponseEntity<>(mapper.map(credit, CreditDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createCredit(@Valid @RequestBody CreditDTO dtoRequest) throws Exception {
        Credit credit = service.create(mapper.map(dtoRequest, Credit.class));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(credit.getCreditId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/requests")
    public ResponseEntity<List<PaymentResponseDTO>> requestCredit(@Valid @RequestBody CreditDTO dtoRequest) throws Exception {
        List<PaymentResponseDTO> list = service.requestCredit(mapper.map(dtoRequest, Credit.class))
                .stream()
                .map(c -> mapper.map(c, PaymentResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    @PutMapping
    public ResponseEntity<CreditDTO> updateCredit(@RequestBody CreditDTO dtoRequest) throws Exception {
        Credit credit = service.getById(dtoRequest.getCreditId());
        if (credit == null) {
            throwCreditNotFound(dtoRequest.getCreditId());
        }
        Credit obj = service.update(mapper.map(dtoRequest, Credit.class));
        return new ResponseEntity<>(mapper.map(obj, CreditDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable("id") Integer id) throws Exception {
        Credit credit = service.getById(id);
        if (credit == null) {
            throwCreditNotFound(id);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void throwCreditNotFound(Integer id) {
        throw new ModelNotFoundException(
                msg.getMessage("credit.notFound",
                new Integer[]{id},
                LocaleContextHolder.getLocale()));
    }
}
