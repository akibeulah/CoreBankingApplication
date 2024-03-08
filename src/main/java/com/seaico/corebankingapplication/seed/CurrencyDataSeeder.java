package com.seaico.corebankingapplication.seed;

import com.seaico.corebankingapplication.models.Currency;
import com.seaico.corebankingapplication.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CurrencyDataSeeder implements ApplicationRunner {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyDataSeeder(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (currencyRepository.count() == 0) {
            Currency ngn = new Currency("Nigerian Naira", "NGN", "kobo", "₦");
            Currency usd = new Currency("US Dollar", "USD", "cent", "$");

            currencyRepository.save(ngn);
            currencyRepository.save(usd);

            System.out.println("Initial currency entries seeded successfully.");
        }
    }
}
