package com.seaico.corebankingapplication.mapper;

import com.seaico.corebankingapplication.dto.account.AccountDetails;
import com.seaico.corebankingapplication.models.Account;

public class AccountMapper {
    public static AccountDetails accountToAccountDetails(Account account) {
        return new AccountDetails(
                account.getAccountName(),
                account.getDescription(),
                account.getAccountNumber(),
                account.getStatus(),
                account.getCurrency()
        );
    }
}
