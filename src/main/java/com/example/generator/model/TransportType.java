package com.example.generator.model;

public enum TransportType {
    TRAIN(0),
    PLANE(1),
    BUS(2);
    private final int code;

    TransportType(int code) {
        this.code = code;
    }

    public static TransportType getByCode(int code) {
        TransportType[] values = TransportType.values();
        for (TransportType value : values) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("No TransportType found by code " + code);
    }
}
