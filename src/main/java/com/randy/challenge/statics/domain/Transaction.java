package com.randy.challenge.statics.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {

    private final double amount;
    private final long preformedAt;
}
