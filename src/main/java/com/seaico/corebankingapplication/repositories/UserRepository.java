package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUsername(String userName);
    List<User> findByEmail(String userName);
}
