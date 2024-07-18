package it.forcina.co2_tracking_core.exception;

import lombok.Getter;

@Getter
public class CO2ReadingException extends Exception {
    private String errorCode;

    public CO2ReadingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
