package com.xmlan.machine.common.object

enum AlgorithmEnum {

    MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256")

    final String type

    AlgorithmEnum(String type) {
        this.type = type
    }

}
