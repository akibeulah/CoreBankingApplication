package com.seaico.corebankingapplication.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewAccountRequest {
    private String accountName;
    private String description;
    private String currency;
    private String pin;
}
