package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Pin;
import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<Pin, String> {
    List<Pin> findAllByUserId(User userId);
}
