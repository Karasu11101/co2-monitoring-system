package it.forcina.co2_tracking_core.exception;

import lombok.Getter;

@Getter
public class SensorException extends Exception {
    private String errorCode;

    public SensorException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
