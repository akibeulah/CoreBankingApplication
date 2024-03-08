package com.seaico.corebankingapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account accountId;
    private int balanceBeforeTransaction;
    private int balanceAfterTransaction;
    private LocalDateTime dateCreated;

    @Override
    public String toString() {
        return "AccountHistory{" +
                "id='" + id + '\'' +
                ", accountId=" + accountId.getAccountNumber() +
                ", balanceBeforeTransaction=" + balanceBeforeTransaction +
                ", balanceAfterTransaction=" + balanceAfterTransaction +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
