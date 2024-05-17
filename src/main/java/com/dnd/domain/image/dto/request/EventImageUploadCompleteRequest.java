package com.dnd.domain.image.dto.request;

import com.dnd.domain.image.domain.ImageFileExtension;

import io.swagger.v3.oas.annotations.media.Schema;

public record EventImageUploadCompleteRequest(
	Long eventId,
	@Schema(description = "이미지 파일의 확장자", defaultValue = "JPEG")
	ImageFileExtension imageFileExtension
) {
}
