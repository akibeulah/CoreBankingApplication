package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.enums.AccountStatus;
import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    private ActivityService activityService;
    private CurrencyService currencyService;
    private UserService userService;
    private AccountBalanceService accountBalanceService;
    private AccountHistoryService accountHistoryService;

    public boolean isCurrentUserOwnerOfAccount(User user, String accountNumber) {
        return fetchByAccountNumber(accountNumber).getUserId().equals(user);
    }

    public Account createAccount(
            User user,
            String description,
            String currency
    ) {
        try {
            Account account = new Account(
                    UUID.randomUUID().toString(),
                    user.getFullName(),
                    description,
                    generateAccountNumber(),
                    AccountStatus.ACTIVE,
                    currencyService.fetchCurrency(currency),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    user
            );
            accountRepository.save(account);
            activityService.createActivity(new Activity(
                    UUID.randomUUID().toString(),
                    "New %s account created".formatted(account.getCurrency()),
                    LocalDateTime.now(),
                    user
            ));
            accountBalanceService.createAccountBalanceCreditEntries(account, 0);
            accountHistoryService.createAccountHistoryCreditEntry(account, 0);

            return account;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateAccount(Account sender, Account recipient, int amount) {
        try {
            // Update sender
            activityService.createActivity(new Activity(
                    UUID.randomUUID().toString(),
                    "Sending %s%d %s to %s".formatted(sender.getCurrency().getDenomination(), amount, sender.getCurrency().getDenomination(), recipient.getUserId().getId()),
                    LocalDateTime.now(),
                    userService.fetchUserByUsername(sender.getUserId().getUsername()).orElseThrow(() -> new EntityNotFoundException("User not Found"))
            ));

            accountBalanceService.createAccountBalanceDebitEntries(sender, amount);
            accountHistoryService.createAccountHistoryDebitEntry(sender, amount);

            // Update recipient
            activityService.createActivity(new Activity(
                    UUID.randomUUID().toString(),
                    "Receiving %s%d %s from %s".formatted(recipient.getCurrency().getDenomination(), amount, recipient.getCurrency().getDenomination(), sender.getUserId().getId()),
                    LocalDateTime.now(),
                    userService.fetchUserByUsername(recipient.getUserId().getUsername()).orElseThrow(() -> new EntityNotFoundException("User not Found"))
            ));

            accountBalanceService.createAccountBalanceCreditEntries(recipient, amount);
            accountHistoryService.createAccountHistoryCreditEntry(recipient, amount);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String generateAccountNumber() {
        LocalDateTime currTimeStamp = LocalDateTime.now();
        String timestampString = currTimeStamp.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

        long accountCount = accountRepository.countByDateCreatedBetween(
                LocalDateTime.of(currTimeStamp.toLocalDate(), LocalTime.MIN),
                LocalDateTime.of(currTimeStamp.toLocalDate(), LocalTime.MAX)
        );

        String paddedAccountCount = String.format("%05d", accountCount);
        return timestampString + paddedAccountCount;
    }

    public List<Account> fetchAllByUserId(User user) {
        return accountRepository.findAllByUserId(user);
    }

    public Account fetchByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
