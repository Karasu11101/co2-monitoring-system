package it.forcina.co2_tracking_core.dto.response;

public enum Codes {
    INVALID_ARGUMENT("ERROR_CODE_001"),
    NO_RESOURCE_FOUND("ERROR_CODE_002"),
    OPERATION_UNSUCCESSFUL("ERROR_CODE_003");

    private final String label;

    Codes(String label) { this.label = label; }

    public String getLabel() { return this.label; }
}
