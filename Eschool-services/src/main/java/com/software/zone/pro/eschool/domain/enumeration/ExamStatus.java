package com.software.zone.pro.eschool.domain.enumeration;

import java.util.Objects;

public enum ExamStatus {

    PROGRAMMATIC("PROGRAMMATIC"),
    DONE("DONE"),
    CORRECTED("CORRECTED"),
    MISSED("MISSED");

    private String value;

    ExamStatus(String value) {
        this.value = value;
    }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public static ExamStatus forValue(String value) {
        for (ExamStatus examStatus : values()) {
            if (Objects.equals(examStatus.getValue(), value)) {
                return examStatus;
            }
        }
        return null;
    }
}
