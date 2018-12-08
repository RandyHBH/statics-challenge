package com.randy.challenge.statics.controller;

import com.randy.challenge.statics.service.transaction.TransactionDAO;
import com.randy.challenge.statics.service.transaction.TransactionResponse;
import com.randy.challenge.statics.service.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity saveTransaction(@RequestBody TransactionBodyDAO body) {
        TransactionResponse response = transactionService
                .save(new TransactionDAO(body.getAmount(), body.getPreformedAt()));

        return response == TransactionResponse.CREATED
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.noContent().build();
    }
}
