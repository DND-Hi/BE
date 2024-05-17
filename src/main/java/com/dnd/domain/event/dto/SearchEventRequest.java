package com.dnd.domain.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SearchEventRequest {
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;

    private int distance;
}
