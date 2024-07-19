package it.forcina.co2_tracking_core.handler;

import it.forcina.co2_tracking_core.dto.response.Response;
import it.forcina.co2_tracking_core.exception.CO2ReadingException;
import it.forcina.co2_tracking_core.exception.CityException;
import it.forcina.co2_tracking_core.exception.DistrictException;
import it.forcina.co2_tracking_core.exception.SensorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CityException.class)
    public ResponseEntity<Response> handleCityException(CityException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new Response.Builder<>()
                        .message(e.getMessage())
                        .info(e.getErrorCode())
                        .build()
        );
    }

    @ExceptionHandler(SensorException.class)
    public ResponseEntity<Response> handleSensorException(SensorException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new Response.Builder<>()
                        .message(e.getMessage())
                        .info(e.getErrorCode())
                        .build()
        );
    }

    @ExceptionHandler(DistrictException.class)
    public ResponseEntity<Response> handleDistrictException(DistrictException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new Response.Builder<>()
                        .message(e.getMessage())
                        .info(e.getErrorCode())
                        .build()
        );
    }

    @ExceptionHandler(CO2ReadingException.class)
    public ResponseEntity<Response> handleReadingsException(CO2ReadingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new Response.Builder<>()
                        .message(e.getMessage())
                        .info(e.getErrorCode())
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new Response.Builder<>()
                        .message(e.getMessage())
                        .build()
        );
    }
}
