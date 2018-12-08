package com.randy.challenge.statics.service.transaction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class TransactionDAO {

    private final double amount;
    private final long preformedAt;
}
