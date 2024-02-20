package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.repositories.AccountRepository;
import com.seaico.corebankingapplication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/accounts/")
public class AccountController extends BaseController {
    final AccountRepository accountRepository;
    final UserRepository userRepository;

    @GetMapping("all-user-accounts")
    public ResponseEntity<List<Account>> fetchAllUserAccounts() {
        List<Account> accounts = accountRepository.findAllByUserId(userRepository.findByUsername(getUsername()).orElseThrow());
        return ResponseEntity.ok(accounts);
    }
}
