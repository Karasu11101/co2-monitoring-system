package it.forcina.co2_tracking_core.exception;

import lombok.Getter;

@Getter
public class CityException extends Exception {
    private final String errorCode;

    public CityException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
