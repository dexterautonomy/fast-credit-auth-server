package com.hingebridge.devops.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseConstants {
	SUCCESSFUL("00", "Operation successful"),
	FAILURE("99", "Operation failed"),
	INVALIDFIELDS("81", "Selected field(s) has/have invalid value(s) in payload");

	private String code;
	private String message;
}