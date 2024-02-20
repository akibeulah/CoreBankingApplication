package com.seaico.corebankingapplication.models;

import com.seaico.corebankingapplication.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String accountName;
    private String description;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
}
