package br.com.meetime.hubspotintegration.service.impl;

import br.com.meetime.hubspotintegration.client.HubSpotClient;
import br.com.meetime.hubspotintegration.dto.request.ContactRequest;
import br.com.meetime.hubspotintegration.dto.response.ContactResponse;
import br.com.meetime.hubspotintegration.dto.response.ContactWebHookResponse;
import br.com.meetime.hubspotintegration.dto.response.GetContactResponseDTO;
import br.com.meetime.hubspotintegration.service.ContactService;
import br.com.meetime.hubspotintegration.utils.SecretUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final HubSpotClient client;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void create(String accessToken, ContactRequest contact) {
        LocalTime start = LocalTime.now();
        log.info("Starting contact creation");

        try {
            client.createContact(accessToken, Map.of("properties", contact));
        } catch (FeignException.Conflict e) {
            log.error("Contact already exists: {}", SecretUtils.hideSecret(e.getMessage()));
            throw e;
        } catch (FeignException e) {
            log.error("A FeignException occurred while creating contact: {}", SecretUtils.hideSecret(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error("Error creating contact", e);
            throw e;
        } finally {
            log.info("Contact creation took: {} ms", ChronoUnit.MILLIS.between(start, LocalTime.now()));
        }
    }

    @Override
    public List<ContactResponse> find(String accessToken) {
        LocalTime start = LocalTime.now();
        log.info("Starting contact find");

        try {
            GetContactResponseDTO response = client.findContact(accessToken);
            return response.getResults();
        } catch (FeignException e) {
            log.error("A FeignException occurred while finding contact: {}", SecretUtils.hideSecret(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error("Error finding contact", e);
            throw e;
        } finally {
            log.info("Contact find took: {} ms", ChronoUnit.MILLIS.between(start, LocalTime.now()));
        }
    }

    @Override
    public void processCreated(List<ContactWebHookResponse> response) {
        log.info("Processing created contacts: {}", response);
        messagingTemplate.convertAndSend("/topic/contacts", response);
    }
}
