package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    Optional<Currency> findByName(String name);
    Optional<Currency> findByShortCode(String shortCode);
}
