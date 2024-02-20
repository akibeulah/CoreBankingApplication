package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.AccountRepository;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    private ActivityService activityService;

    public void createAccount(Account account) {
        try {
            accountRepository.save(account);
            activityService.createActivity(new Activity(
                    UUID.randomUUID().toString(),
                    "New %s account created".formatted(account.getCurrency()),
                    LocalDateTime.now(),
                    account.getUserId()
            ));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
