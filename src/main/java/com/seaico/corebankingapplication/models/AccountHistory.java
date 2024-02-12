package com.seaico.corebankingapplication.models;

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
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String accountId;
    private int balanceBeforeTransaction;
    private int balanceAfterTransaction;
    private String dateCreated;
}
