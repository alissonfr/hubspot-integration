package br.com.meetime.hubspotintegration.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecretUtils {

    public static String hideSecret(String message) {
        return message.replaceAll("(?i)client_secret=[^&]*", "client_secret=xxxx")
                .replaceAll("(?i)client_id=[^&]*", "client_id=xxxx");
    }
}
