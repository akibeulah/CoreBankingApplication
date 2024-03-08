package com.seaico.corebankingapplication.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seaico.corebankingapplication.dto.user.UserAuthenticationPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("user_details")
    private UserAuthenticationPayload user;
}
