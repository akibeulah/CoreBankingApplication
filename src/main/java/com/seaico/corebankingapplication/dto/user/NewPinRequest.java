package com.seaico.corebankingapplication.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewPinRequest {
    private String oldPin;
    private String newPin;
    private String confirmNewPin;
    private String password;

    @Override
    public String toString() {
        return "NewPinRequest{" +
                "oldPin='" + oldPin + '\'' +
                ", newPin='" + newPin + '\'' +
                ", confirmNewPin='" + confirmNewPin + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
