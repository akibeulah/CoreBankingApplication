package com.seaico.corebankingapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seaico.corebankingapplication.dto.user.UserBasicProfileDto;
import com.seaico.corebankingapplication.mapper.UserMapper;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    public UserBasicProfileDto getUserId() {
        return UserMapper.userToUserBasicProfileDto(userId);
    }
}
