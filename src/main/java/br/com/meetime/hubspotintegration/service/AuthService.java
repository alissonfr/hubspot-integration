package br.com.meetime.hubspotintegration.service;

import br.com.meetime.hubspotintegration.dto.response.HubSpotTokenResponse;

public interface AuthService {
    String getOAuthUri();
    HubSpotTokenResponse getAccessToken(String code);
}
