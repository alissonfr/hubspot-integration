package br.com.meetime.hubspotintegration.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HubSpotConstants {

    public static final String RESPONSE_TYPE = "code";
    public static final String SCOPES = "crm.objects.contacts.write crm.objects.contacts.read " +
            "crm.schemas.contacts.read crm.schemas.contacts.write oauth";
    public static final String HUB_SPOT_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String GRANT_TYPE = "authorization_code";
    public static String BASE_URL;
    public static String OAUTH_PATH;
    public static String CLIENT_ID;
    public static String CLIENT_SECRET;
    public static String REDIRECT_URI;

    @Value("${api.hubspot-app.url}")
    public void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    @Value("${api.hubspot-app.path.oauth-authorize}")
    public void setOauthPath(String oauthPath) {
        OAUTH_PATH = oauthPath;
    }

    @Value("${hubspot.client-id}")
    public void setClientId(String clientId) {
        CLIENT_ID = clientId;
    }

    @Value("${hubspot.secret-id}")
    public void setClientSecret(String clientSecret) {
        CLIENT_SECRET = clientSecret;
    }

    @Value("${hubspot.redirect-uri}")
    public void setRedirectUri(String redirectUri) {
        REDIRECT_URI = redirectUri;
    }
}