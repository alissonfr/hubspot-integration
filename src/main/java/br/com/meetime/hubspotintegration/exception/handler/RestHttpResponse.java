package br.com.meetime.hubspotintegration.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class RestHttpResponse {

    private final String code;
    private final String message;
    private final List<String> details;

    public RestHttpResponse(HttpStatus httpStatus, String details) {
        this.code = String.valueOf(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
        this.details = Collections.singletonList(details);
    }
}