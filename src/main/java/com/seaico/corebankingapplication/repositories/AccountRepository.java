package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findAllByUserId(User userId);
}
