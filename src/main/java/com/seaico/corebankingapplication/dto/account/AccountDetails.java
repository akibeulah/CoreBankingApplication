package com.seaico.corebankingapplication.dto.account;

import com.seaico.corebankingapplication.enums.AccountStatus;
import com.seaico.corebankingapplication.models.Currency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {
    private String accountName;
    private String description;
    private String accountNumber;
    private AccountStatus status;
    private Currency currency;
}