package com.seaico.corebankingapplication.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateRequest {
    private String fullName;

    public boolean hasValidChanges() {
        return fullName != null && !fullName.isEmpty();
    }
}
