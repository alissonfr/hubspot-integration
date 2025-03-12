package br.com.meetime.hubspotintegration.controller;

import br.com.meetime.hubspotintegration.client.HubSpotClient;
import br.com.meetime.hubspotintegration.dto.request.HubSpotTokenRequest;
import br.com.meetime.hubspotintegration.dto.response.HubSpotTokenResponse;
import br.com.meetime.hubspotintegration.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static br.com.meetime.hubspotintegration.constant.HubSpotConstants.*;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @GetMapping("/oauth/authorize")
    public ResponseEntity<String> authorize() {
        return ResponseEntity.ok(service.getOAuthUri());
    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<HubSpotTokenResponse> callback(@RequestParam("code") String code) {
        return ResponseEntity.ok(service.getAccessToken(code));
    }



    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> webhookEvent) {
        System.out.println("Webhook recebido: " + webhookEvent);
        return ResponseEntity.ok("Webhook processado");
    }
}
