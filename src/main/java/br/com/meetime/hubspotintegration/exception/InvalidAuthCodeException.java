package br.com.meetime.hubspotintegration.exception;

public class InvalidAuthCodeException extends RuntimeException {
    public InvalidAuthCodeException(String message) {
        super(message);
    }
}
