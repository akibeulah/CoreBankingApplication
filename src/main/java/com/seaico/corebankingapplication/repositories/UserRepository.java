package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String userName);

    Optional<User> findByEmail(String userName);
}
