package com.example.demo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RequestParameterEnum {
	NOTHING("RP_01"),
	INVALID_TYPE("RP_02"),
	WRONG("RP_03"),
	EXISTS("RP_04"),
	NOT_EXISTS("RP_05"),
	NOT_FOUND("RP_06");
	@Getter
	private String name;
}
