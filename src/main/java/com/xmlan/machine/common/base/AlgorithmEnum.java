package com.xmlan.machine.common.base;

public enum AlgorithmEnum {

    MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256");

    private final String type;

    AlgorithmEnum(String type) {
        this.type = type;
    }

    public final String getType() {
        return type;
    }

}
