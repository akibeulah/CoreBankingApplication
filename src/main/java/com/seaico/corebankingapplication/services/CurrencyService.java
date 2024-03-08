package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Currency;
import com.seaico.corebankingapplication.repositories.CurrencyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency fetchCurrency(String currency) {
        return currencyRepository.findByShortCode(currency).orElseThrow(() -> new EntityNotFoundException("Currency does not exist"));
    }
}
