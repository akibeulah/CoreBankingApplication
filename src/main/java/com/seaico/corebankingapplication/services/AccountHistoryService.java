package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.AccountHistory;
import com.seaico.corebankingapplication.repositories.AccountHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AccountHistoryService {
    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    private void createAccountHistoryEntry(Account account, int amount) {
        AccountHistory newAccountHistory = new AccountHistory(
                null,
                account,
                account.getBalance(),
                account.getBalance() + amount,
                LocalDateTime.now()
        );

        accountHistoryRepository.save(newAccountHistory);
    }

    public void createAccountHistoryDebitEntry(Account account, int amount) {
        createAccountHistoryEntry(account, -1 * amount);
    }

    public void createAccountHistoryCreditEntry(Account account, int amount) {
        createAccountHistoryEntry(account, amount);
    }
}
