package br.com.meetime.hubspotintegration.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContactWebHookResponse {
    private String appId;
    private String eventId;
    private String subscriptionId;
    private String portalId;
    private String occurredAt;
    private String subscriptionType;
    private String attemptNumber;
    private String objectId;
    private String changeSource;
    private String changeFlag;
}
