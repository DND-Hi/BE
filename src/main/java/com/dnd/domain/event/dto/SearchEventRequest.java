package com.dnd.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchEventRequest {
    private Double longitude;
    private Double latitude;
    private int distance;
}
