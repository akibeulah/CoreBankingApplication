package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.dto.GenericResponse;
import com.seaico.corebankingapplication.dto.transaction.NewTransactionRequest;
import com.seaico.corebankingapplication.models.Transaction;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.seaico.corebankingapplication.services.JwtService.getUsername;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    final TransactionService transactionService;
    final PinService pinService;
    final UserService userService;
    final AccountService accountService;
    final CurrencyService currencyService;

    @GetMapping("/user")
    public ResponseEntity<List<Transaction>> getAllUserTransactions() {
        return ResponseEntity.ok(transactionService.fetchAllBySenderIdOrRecipientId(getUsername()));
    }

    @PostMapping("/new")
    public ResponseEntity<GenericResponse<Transaction>> createNewTransaction(@RequestBody NewTransactionRequest request) {
        User user = userService.fetchUserByUsername(JwtService.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not Found!"));

        if (!PinService.checkPassword(request.getPin(), user.getPins().get(0).getHashedPinCode()))
            return ResponseEntity.badRequest().body(new GenericResponse<>(
                    400,
                    "Incorrect Pin"
            ));

        if (!Objects.equals(user.getId(), userService.fetchUserByAccount(request.getSenderAccountNumber()).getId()))
            return ResponseEntity.badRequest().body(new GenericResponse<>(
                    400,
                    "Fraudulent activity detected and logged!"
            ));

        try {
            Transaction transaction = transactionService.createTransaction(
                    accountService.fetchByAccountNumber(request.getRecipientAccountNumber()),
                    accountService.fetchByAccountNumber(request.getSenderAccountNumber()),
                    request.getAmount(),
                    request.getDescription(),
                    currencyService.fetchCurrency(request.getCurrency()),
                    request.getTransactionDate().isEmpty() ? LocalDate.now() : LocalDate.parse(request.getTransactionDate())
            );

            if (transaction != null)
                return ResponseEntity.ok(new GenericResponse<>(
                        200,
                        "Transaction created successfully",
                        transaction
                ));
            else
                return ResponseEntity.badRequest().body(new GenericResponse<>(
                        400,
                        "Transaction creation failed"
                ));
        } catch (Exception e) {
            System.out.println(e);

            return ResponseEntity.badRequest().body(new GenericResponse<>(
                    400,
                    "Transaction creation failed"
            ));
        }
    }

    @GetMapping("/transaction/{tRef}")
    public ResponseEntity<GenericResponse<Transaction>> fetchTransactionByReference(@PathVariable String tRef) {
        Transaction transaction = transactionService.fetchTransactionByTransactionReference(tRef);

        if (
                Objects.equals(accountService.fetchByAccountNumber(transaction.getSenderId().getAccountNumber()).getUserId().getUsername(), getUsername()) ||
                        Objects.equals(accountService.fetchByAccountNumber(transaction.getRecipientId().getAccountNumber()).getUserId().getUsername(), getUsername())
        )
            return ResponseEntity.ok(new GenericResponse<>(
                    200,
                    "Transaction fetched successfully",
                    transaction
            ));

        return ResponseEntity.badRequest().body(new GenericResponse<>(
                401,
                "This is not your transaction, fraudulent activity detected"
        ));
    }
}
