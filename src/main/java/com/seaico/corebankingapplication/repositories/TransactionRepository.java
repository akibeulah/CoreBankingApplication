package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Transaction;
import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t WHERE t.senderId.userId = :userId OR t.recipientId.userId = :userId")
    List<Transaction> findAllBySenderIdOrRecipientId(User userId);
    long countByDateCreatedBetween(LocalDateTime start, LocalDateTime end);
    Optional<Transaction> findByTransactionReference(String transactionReference);
}
