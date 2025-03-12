package br.com.meetime.hubspotintegration.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthTokenResponse {

    private String refreshToken;
    private String accessToken;
    private String expiresIn;
}
