package com.dnd.global.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlConstants {

	LOCAL_SERVER_URL("http://localhost:8080"),

	LOCAL_DOMAIN_URL("http://localhost:3000"),
	IMAGE_DOMAIN_URL("https://kr.object.ncloudstorage.com"),
	;
	private final String value;

}
