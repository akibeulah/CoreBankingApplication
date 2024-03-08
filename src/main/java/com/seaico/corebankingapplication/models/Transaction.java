package com.seaico.corebankingapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seaico.corebankingapplication.dto.account.AccountDetails;
import com.seaico.corebankingapplication.mapper.AccountMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Account senderId;
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Account recipientId;
    private int amount;
    private String description;
    @ManyToOne
    @JoinColumn(name = "currency", nullable = false)
    private Currency currency;
    private LocalDate transactionDate;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private String transactionReference;

    public AccountDetails getSenderId() {
        return AccountMapper.accountToAccountDetails(senderId);
    }

    public AccountDetails getRecipientId() {
        return AccountMapper.accountToAccountDetails(recipientId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", senderId=" + senderId.getAccountName() +
                ", recipientId=" + recipientId.getAccountNumber() +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", currency=" + currency.getSymbol() +
                ", transactionDate=" + transactionDate +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", transactionReference='" + transactionReference + '\'' +
                '}';
    }
}
