package br.com.meetime.hubspotintegration.service.impl;

import br.com.meetime.hubspotintegration.client.HubSpotClient;
import br.com.meetime.hubspotintegration.dto.response.AuthTokenResponse;
import br.com.meetime.hubspotintegration.dto.response.HubSpotTokenResponse;
import br.com.meetime.hubspotintegration.service.AuthService;
import br.com.meetime.hubspotintegration.utils.SecretUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static br.com.meetime.hubspotintegration.constant.HubSpotConstants.*;
import static br.com.meetime.hubspotintegration.constant.HubSpotConstants.REDIRECT_URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final HubSpotClient client;

    @Override
    public String getOAuthUri() {
        LocalTime start = LocalTime.now();
        log.info("Starting OAuth URI generation");

        try {
            return UriComponentsBuilder.fromUriString(BASE_URL + OAUTH_PATH)
                    .queryParam("client_id", CLIENT_ID)
                    .queryParam("redirect_uri", REDIRECT_URI)
                    .queryParam("scope", "crm.objects.contacts.write crm.objects.contacts.read crm.schemas.contacts.read crm.schemas.contacts.write oauth")
                    .queryParam("response_type", "code")
                    .toUriString();
        } catch (Exception e) {
            log.error("Error generating OAuth URI", e);
            throw e;
        } finally {
            log.info("URI generation took: {} ms", ChronoUnit.MILLIS.between(start, LocalTime.now()));
        }
    }

    @Override
    public AuthTokenResponse getAccessToken(String code) {
        LocalTime start = LocalTime.now();
        log.info("Starting access token generation");

        try {
            HubSpotTokenResponse response = client.getAccessToken(HUB_SPOT_CONTENT_TYPE, GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, code);

            return AuthTokenResponse.builder()
                    .refreshToken(response.getRefreshToken())
                    .accessToken(response.getAccessToken())
                    .expiresIn(response.getExpiresIn())
                    .build();
        } catch (FeignException e) {
            log.error("A FeignException occurred while generating access token: {}", SecretUtils.hideSecret(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error("Error generating access token", e);
            throw e;
        } finally {
            log.info("Access token generation took: {} ms", ChronoUnit.MILLIS.between(start, LocalTime.now()));
        }
    }
}
