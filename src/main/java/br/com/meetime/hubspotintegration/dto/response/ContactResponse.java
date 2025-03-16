package br.com.meetime.hubspotintegration.dto.response;

import br.com.meetime.hubspotintegration.dto.request.ContactRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactResponse {

    private String id;
    private ContactRequest properties;
    private String updatedAt;
    private String createdAt;
}
