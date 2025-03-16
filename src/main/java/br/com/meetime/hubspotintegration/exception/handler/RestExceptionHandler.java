package br.com.meetime.hubspotintegration.exception.handler;

import br.com.meetime.hubspotintegration.dto.response.RestHttpResponse;
import br.com.meetime.hubspotintegration.exception.InvalidAuthCodeException;
import br.com.meetime.hubspotintegration.exception.RateLimitException;
import br.com.meetime.hubspotintegration.utils.SecretUtils;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestHttpResponse> handleException(Exception ex) {
        RestHttpResponse response = new RestHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, SecretUtils.hideSecret(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestHttpResponse handleFeignExceptionUnauthorized(FeignException.Unauthorized ex) {
        return new RestHttpResponse(HttpStatus.UNAUTHORIZED, SecretUtils.hideSecret(ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestHttpResponse handleFeignException(FeignException ex) {
        return new RestHttpResponse(HttpStatus.BAD_REQUEST, SecretUtils.hideSecret(ex.getMessage()));
    }

    @ExceptionHandler(InvalidAuthCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestHttpResponse handleInvalidAuthCodeException(InvalidAuthCodeException ex) {
        return new RestHttpResponse(HttpStatus.BAD_REQUEST, SecretUtils.hideSecret(ex.getMessage()));
    }

    @ExceptionHandler(RateLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public RestHttpResponse handleRateLimitException(RateLimitException ex) {
        return new RestHttpResponse(HttpStatus.TOO_MANY_REQUESTS, SecretUtils.hideSecret(ex.getMessage()));
    }
}
