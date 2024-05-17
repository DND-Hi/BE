package com.dnd.domain.event.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchEventResponse {
    private Long id;
    private String title;
    private String description;
    private String host;
    private Double longitude;
    private Double latitude;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
}
