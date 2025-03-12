package br.com.meetime.hubspotintegration.client;

import br.com.meetime.hubspotintegration.dto.response.HubSpotTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "hubSpotClient", url = "${api.hubspot-api.url}")
public interface HubSpotClient {

    @PostMapping(value = "${api.hubspot-api.path.oauth-token}", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    HubSpotTokenResponse getAccessToken(
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    );

}