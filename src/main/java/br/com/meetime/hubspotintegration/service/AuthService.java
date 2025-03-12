package br.com.meetime.hubspotintegration.service;

import br.com.meetime.hubspotintegration.dto.response.AuthTokenResponse;

public interface AuthService {
    String getOAuthUri();
    AuthTokenResponse getAccessToken(String code);
}
