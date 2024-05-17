package com.dnd.global.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlConstants {

	LOCAL_SERVER_URL("http://localhost:8080"),

	LOCAL_DOMAIN_URL("http://localhost:5173"),
	;
	private final String value;

}
