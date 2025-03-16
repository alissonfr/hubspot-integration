package br.com.meetime.hubspotintegration.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketConstants {

    public static final String CREATED_CONTACTS_TOPIC = "/topic/contacts";
}
