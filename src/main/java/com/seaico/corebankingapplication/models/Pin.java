package com.seaico.corebankingapplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String hashedPinCode;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User userId;
    @Column(nullable = false)
    private LocalDateTime dateCreated;
}
