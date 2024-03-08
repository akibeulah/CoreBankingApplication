package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, String> {
}
