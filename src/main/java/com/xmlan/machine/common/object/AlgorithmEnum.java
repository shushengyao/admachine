package com.xmlan.machine.common.object;

public enum AlgorithmEnum {

    MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256");

    private String type;

    AlgorithmEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
