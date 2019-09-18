package com.software.zone.pro.eschool.domain.enumeration;

public enum UserType {

    FOUNDER(0),
    TEACHER(1),
    DIRECTOR(2),
    PARENT(3),
    STUDENT(4),
    UNKNOWN(5);

    private int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static UserType forValue(int value) {
        for (UserType mediaType : values()) {
            if (mediaType.getValue() == value) {
                return mediaType;
            }
        }
        return null;
    }
}
