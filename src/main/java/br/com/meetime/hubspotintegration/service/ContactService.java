package br.com.meetime.hubspotintegration.service;

import br.com.meetime.hubspotintegration.dto.request.ContactRequest;
import br.com.meetime.hubspotintegration.dto.response.ContactResponse;
import br.com.meetime.hubspotintegration.dto.response.ContactWebHookResponse;

import java.util.List;

public interface ContactService {
    void create(String accessToken, ContactRequest contact);
    List<ContactResponse> find(String accessToken);
    void processCreated(List<ContactWebHookResponse> contacts);
}
