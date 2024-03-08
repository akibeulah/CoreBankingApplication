package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findAllByUserId(User userId);
    long countByDateCreatedBetween(LocalDateTime start, LocalDateTime end);
    Account findByAccountNumber(String accountNumber);
}
