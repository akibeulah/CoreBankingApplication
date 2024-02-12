package com.seaico.corebankingapplication.models;

import com.seaico.corebankingapplication.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String accountName;
    private String description;
    private AccountStatus status;
    private String currency;
    private String dateCreated;
    private String dateUpdated;
}
