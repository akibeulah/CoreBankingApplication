package com.seaico.corebankingapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String shortCode;
    private String denomination;
    private String symbol;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    @OneToMany(mappedBy = "currency", cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Account> accountsList;
    @OneToMany(mappedBy = "currency", cascade = CascadeType.DETACH)
    @JsonIgnore
    private List<Transaction> transactionList;

    public Currency(String name, String shortCode, String denomination, String symbol) {
        this.name = name;
        this.shortCode = shortCode;
        this.denomination = denomination;
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
        this.symbol = symbol;
    }

    public long getTransactionList() {
        return transactionList.size();
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortCode='" + shortCode + '\'' +
                ", denomination='" + denomination + '\'' +
                ", symbol='" + symbol + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", accountsList=" + accountsList.size() +
                ", transactionList=" + transactionList.size() +
                '}';
    }
}
