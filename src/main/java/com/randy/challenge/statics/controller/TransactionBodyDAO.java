package com.randy.challenge.statics.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class TransactionBodyDAO {

    private final double amount;
    private final long preformedAt;

    public TransactionBodyDAO(
            @JsonProperty(value = "amount", required = true) double amount,
            @JsonProperty(value = "preformedAt", required = true) long preformedAt) {
        this.amount = amount;
        this.preformedAt = preformedAt;
    }
}
