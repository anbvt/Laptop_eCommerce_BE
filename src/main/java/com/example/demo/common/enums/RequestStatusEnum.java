package com.example.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RequestStatusEnum {
    FAILURE("RS_00"),
    SUCCESS("RS_01");
    @Getter
    private String response;
}
