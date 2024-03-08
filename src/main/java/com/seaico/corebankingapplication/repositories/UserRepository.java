package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String userName);
    Optional<User> findByEmail(String userName);
    @Query("SELECT u FROM User u INNER JOIN u.accounts a WHERE a.accountNumber = :accountNumber")
    Optional<User> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
