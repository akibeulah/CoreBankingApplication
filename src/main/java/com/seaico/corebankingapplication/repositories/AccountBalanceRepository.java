package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, String> {
}
