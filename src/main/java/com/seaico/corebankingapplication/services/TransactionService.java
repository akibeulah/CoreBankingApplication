package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.Currency;
import com.seaico.corebankingapplication.models.Transaction;
import com.seaico.corebankingapplication.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    private AccountService accountService;
    private UserService userService;

    public Transaction createTransaction(Account recipient, Account sender, int amount, String description, Currency currency, LocalDate transactionDate) {
        try {
            Transaction transaction = new Transaction(
                    UUID.randomUUID().toString(),
                    sender,
                    recipient,
                    amount,
                    description,
                    currency,
                    transactionDate == null ? LocalDate.now() : transactionDate,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    generateTransactionReference()
            );
            accountService.updateAccount(sender, recipient, amount);
            transactionRepository.save(transaction);

            return transaction;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Transaction> fetchAllTransactions(String username) {
        List<Transaction> transactions = transactionRepository.findAllBySenderIdOrRecipientId(userService.fetchUserByUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist")));
        transactions.sort(Comparator.comparing(Transaction::getDateCreated).reversed());

        return transactions;
    }

    private String generateTransactionReference() {
        LocalDateTime currTimeStamp = LocalDateTime.now();
        String timestampString = currTimeStamp.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

        long accountCount = transactionRepository.countByDateCreatedBetween(
                LocalDateTime.of(currTimeStamp.toLocalDate(), LocalTime.MIN),
                LocalDateTime.of(currTimeStamp.toLocalDate(), LocalTime.MAX)
        );

        String paddedAccountCount = String.format("TXN%010d", accountCount);
        return timestampString + paddedAccountCount;
    }

    public List<Transaction> fetchAllBySenderIdOrRecipientId(String username) {
        return transactionRepository.findAllBySenderIdOrRecipientId(userService.fetchUserByUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist")));
    }

    public Transaction fetchTransactionByTransactionReference(String tRef) {
        return transactionRepository.findByTransactionReference(tRef).orElseThrow(() -> new EntityNotFoundException("Cannot find transaction!"));
    }
}
