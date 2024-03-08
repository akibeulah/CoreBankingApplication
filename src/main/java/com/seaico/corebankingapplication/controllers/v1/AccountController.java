package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.dto.GenericResponse;
import com.seaico.corebankingapplication.dto.account.NewAccountRequest;
import com.seaico.corebankingapplication.dto.user.UserBasicProfileDto;
import com.seaico.corebankingapplication.mapper.UserMapper;
import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.Currency;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.seaico.corebankingapplication.services.JwtService.getUsername;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/accounts/")
public class AccountController {
    final AccountService accountService;
    final UserService userService;
    final ActivityService activityService;

    @GetMapping("all-user-accounts")
    public ResponseEntity<List<Account>> fetchAllUserAccounts() {
        List<Account> accounts = accountService.fetchAllByUserId(userService.fetchUserByUsername(getUsername()).orElseThrow());
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("get-user-from-account/{accountNumber}")
    public ResponseEntity<GenericResponse<UserBasicProfileDto>> fetchUserDetailsByAccountNumber(@PathVariable String accountNumber) {
        User user = userService.fetchUserByUsername(getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (accountService.isCurrentUserOwnerOfAccount(user, accountNumber))
            return ResponseEntity.badRequest().body(new GenericResponse<>(
                    401,
                    "This is not your account"
            ));

        activityService.createActivity("Fetched owned accounts", user);
        return ResponseEntity.ok(new GenericResponse<>(
                200,
                "Account retrieved successfully",
                UserMapper.userToUserBasicProfileDto(userService.fetchUserByAccount(accountNumber))
        ));
    }

    @GetMapping("get-account/{accountNumber}")
    public ResponseEntity<GenericResponse<Account>> fetchAccount(@PathVariable String accountNumber) {
        User user = userService.fetchUserByUsername(getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (accountService.isCurrentUserOwnerOfAccount(user, accountNumber))
            return ResponseEntity.badRequest().body(new GenericResponse<>(
                    401,
                    "This is not your account"
            ));

        activityService.createActivity("Fetched owned account: " + accountNumber, user);
        Account byAccountNumber = accountService.fetchByAccountNumber(accountNumber);

        return ResponseEntity.ok(new GenericResponse<>(
                200,
                "Account Retrieved Successfully",
                byAccountNumber
        ));
    }

    @PostMapping("request-new")
    public ResponseEntity<GenericResponse<Account>> requestNewAccount(@RequestBody NewAccountRequest request) {
        User user = userService.fetchUserByUsername(JwtService.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not Found!"));

        if (user.getAccounts().stream().map(Account::getCurrency).map(Currency::getShortCode).anyMatch(shortcode -> shortcode.equals(request.getCurrency())))
            return ResponseEntity.badRequest().body(new GenericResponse<>(
                    404,
                    "You already have an account with this currency"
            ));

        if (!PinService.checkPassword(request.getPin(), user.getPins().get(0).getHashedPinCode()))
            return ResponseEntity.badRequest().body(new GenericResponse<>(
                    404,
                    "Account creation failed, incorrect pin"
            ));

        Account account = accountService.createAccount(
                user,
                request.getDescription(),
                request.getCurrency()
        );

        if (account != null)
            return ResponseEntity.ok(new GenericResponse<>(
                    200,
                    "Account created successfully",
                    account
            ));
        else
            return ResponseEntity.ok(new GenericResponse<>(
                    200,
                    "Account creation failed",
                    null
            ));
    }

}
