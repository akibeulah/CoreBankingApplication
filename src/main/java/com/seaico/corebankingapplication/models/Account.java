package com.seaico.corebankingapplication.models;

import com.seaico.corebankingapplication.dto.user.UserBasicProfileDto;
import com.seaico.corebankingapplication.enums.AccountStatus;
import com.seaico.corebankingapplication.mapper.UserMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String accountName;
    private String description;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    @JoinColumn(name = "currency", nullable = false)
    private Currency currency;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    @OneToMany(mappedBy = "accountId", cascade = CascadeType.DETACH)
    private List<AccountBalance> accountBalanceList;
    @OneToMany(mappedBy = "senderId", cascade = CascadeType.DETACH)
    private List<Transaction> senderTransactionsList;
    @OneToMany(mappedBy = "recipientId", cascade = CascadeType.DETACH)
    private List<Transaction> recipeintTransactionsList;
    @OneToMany(mappedBy = "accountId", cascade = CascadeType.DETACH)
    private List<AccountHistory> accountHistoriesList;


    public Account(String id, String accountName, String description, String accountNumber, AccountStatus status, Currency currency, LocalDateTime dateCreated, LocalDateTime dateUpdated, User userId) {
        this.id = id;
        this.accountName = accountName;
        this.description = description;
        this.accountNumber = accountNumber;
        this.status = status;
        this.currency = currency;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.userId = userId;
    }

    public UserBasicProfileDto getUserId() {
        return UserMapper.userToUserBasicProfileDto(userId);
    }

    public List<AccountBalance> getAccountBalanceList() {
        int endIndex = Math.min(accountBalanceList.size(), 5);
        return accountBalanceList.subList(0, endIndex);
    }

    public int getBalance() {
        if (accountBalanceList.isEmpty())
            return 0;
        else
            return accountBalanceList.get(accountBalanceList.size() - 1).getBalance();
    }

    public List<Transaction> getSenderTransactionsList() {
        int endIndex = Math.min(senderTransactionsList.size(), 5);
        return senderTransactionsList.subList(0, endIndex);
    }

    public List<Transaction> getRecipientTransactionsList() {
        int endIndex = Math.min(recipeintTransactionsList.size(), 5);
        return recipeintTransactionsList.subList(0, endIndex);
    }
    public List<Transaction> getTransactions() {
        List<Transaction> transactionList = new ArrayList<>(senderTransactionsList);
        transactionList.addAll(recipeintTransactionsList);

        transactionList.sort(Comparator.comparing(Transaction::getDateCreated).reversed());

        int endIndex = Math.min(transactionList.size(), 5);
        return transactionList.subList(0, endIndex);
    }
}
