package br.com.meetime.hubspotintegration.controller;

import br.com.meetime.hubspotintegration.dto.response.AuthTokenResponse;
import br.com.meetime.hubspotintegration.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<AuthTokenResponse> callback(@RequestParam("code") String code) {
        return ResponseEntity.ok(service.getAccessToken(code));
    }

    // EXTRA ROUTE: automatically redirect user to HubSpot OAuth page
    @GetMapping("/oauth/authorize/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", service.getOAuthUri())
                .build();
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> webhookEvent) {
        System.out.println("Webhook recebido: " + webhookEvent);
        return ResponseEntity.ok("Webhook processado");
    }
}
