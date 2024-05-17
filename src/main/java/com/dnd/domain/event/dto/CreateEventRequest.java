package com.dnd.domain.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateEventRequest {
    @NotBlank
    private String title;

    private String description;

    private String host;

    @NotNull(message = "올바르지 않은 경도입니다.")
    private Double longitude;

    @NotNull(message = "올바르지 않은 위도입니다.")
    private Double latitude;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    private String reservationUrl;

    private Integer cost;

    private String imageUrl;
}
