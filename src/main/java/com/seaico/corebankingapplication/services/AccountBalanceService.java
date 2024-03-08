package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.AccountBalance;
import com.seaico.corebankingapplication.repositories.AccountBalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountBalanceService {
    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    private void createAccountBalanceEntries(Account account, int balance) {
        try {
            AccountBalance accountBalance = new AccountBalance(
                    UUID.randomUUID().toString(),
                    balance,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    account
            );
            accountBalanceRepository.save(accountBalance);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createAccountBalanceDebitEntries(Account account, int amount) {
        createAccountBalanceEntries(account, account.getBalance() - amount);
    }

    public void createAccountBalanceCreditEntries(Account account, int amount) {
        createAccountBalanceEntries(account, account.getBalance() + amount);
    }
}
