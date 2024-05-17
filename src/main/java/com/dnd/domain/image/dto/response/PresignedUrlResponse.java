package com.dnd.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresignedUrlResponse(
    @Schema(description = "image URL") String imageUrl,
    @Schema(description = "Presigned URL") String presignedUrl) {
    public static PresignedUrlResponse from(String presignedUrl, String imageUrl) {
        return new PresignedUrlResponse(presignedUrl, imageUrl);
    }
}
