package com.seaico.corebankingapplication.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewTransactionRequest {
    private String recipientAccountNumber;
    private String senderAccountNumber;
    private int amount;
    private String description;
    private String currency;
    private String transactionDate;
    private String pin;
}
