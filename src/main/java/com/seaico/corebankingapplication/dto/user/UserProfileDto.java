package com.seaico.corebankingapplication.dto.user;

import com.seaico.corebankingapplication.enums.Role;
import com.seaico.corebankingapplication.models.Account;
import com.seaico.corebankingapplication.models.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private String fullName;
    private String username;
    private String email;
    private LocalDateTime dateCreated;
    private Role role;
    private List<Activity> activities;
    private List<Account> accounts;
}
