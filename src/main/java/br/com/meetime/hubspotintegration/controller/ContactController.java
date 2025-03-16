package br.com.meetime.hubspotintegration.controller;

import br.com.meetime.hubspotintegration.dto.request.ContactRequest;
import br.com.meetime.hubspotintegration.dto.response.ContactResponse;
import br.com.meetime.hubspotintegration.dto.response.ContactWebHookResponse;
import br.com.meetime.hubspotintegration.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService service;

    @PostMapping("/")
    public ResponseEntity<Void> create(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody @Valid ContactRequest contact
    ) {
        service.create(accessToken, contact);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<ContactResponse>> find(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok(service.find(accessToken));
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody List<ContactWebHookResponse> response) {
        service.processCreated(response);
        return ResponseEntity.ok("Contact successfully processed");
    }
}
