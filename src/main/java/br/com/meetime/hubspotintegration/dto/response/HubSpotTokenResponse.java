package br.com.meetime.hubspotintegration.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HubSpotTokenResponse {
    private String refreshToken;
    private String accessToken;
    private String expiresIn;
}
