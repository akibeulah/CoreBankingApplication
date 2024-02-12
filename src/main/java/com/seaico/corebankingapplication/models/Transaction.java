package com.seaico.corebankingapplication.models;

import com.seaico.corebankingapplication.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String senderId;
    private String recipientId;
    private int amount;
    private String description;
    private String currency;
    private String transactionDate;
    private String dateCreated;
    private String dateUpdated;
}
