package br.com.meetime.hubspotintegration.client;

import br.com.meetime.hubspotintegration.dto.request.HubSpotTokenRequest;
import br.com.meetime.hubspotintegration.dto.response.HubSpotTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "hubSpotClient", url = "${api.hubspot-api.url}")
public interface HubSpotClient {

    @PostMapping(value = "${api.hubspot-api.path.oauth-token}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    HubSpotTokenResponse getAccessToken(@RequestBody HubSpotTokenRequest body);

}