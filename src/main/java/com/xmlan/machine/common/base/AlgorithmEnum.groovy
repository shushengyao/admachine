package com.xmlan.machine.common.base

enum AlgorithmEnum {

    MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256")

    private final String type

    AlgorithmEnum(String type) {
        this.type = type
    }

    final String getType() {
        return type
    }

}
