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
public class AccountBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int balance;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account accountId;

    @Override
    public String toString() {
        return "AccountBalance{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", accountId=" + accountId.getAccountNumber() +
                '}';
    }
}
