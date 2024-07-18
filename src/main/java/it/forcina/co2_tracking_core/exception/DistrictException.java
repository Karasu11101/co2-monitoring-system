package it.forcina.co2_tracking_core.exception;

import lombok.Getter;

@Getter
public class DistrictException extends Exception {
    private String errorCode;

    public DistrictException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
