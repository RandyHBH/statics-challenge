package com.randy.challenge.statics.service.transaction;

import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponse save(TransactionDAO newTransaction) {
        return TransactionResponse.FAILED;
    }
}
