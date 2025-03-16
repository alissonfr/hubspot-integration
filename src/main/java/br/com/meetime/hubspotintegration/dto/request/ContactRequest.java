package br.com.meetime.hubspotintegration.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String firstname;

    private String lastname;

    private String phone;

    private String company;

    private String website;
}
