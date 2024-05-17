package com.dnd.domain.event.dto;

import java.time.LocalDateTime;

public interface SearchEventProjection {
    Long getEventId();
    String getTitle();
    String getDescription();
    String getHost();
    Double getLongitude();
    Double getLatitude();
    LocalDateTime startAt();
    LocalDateTime finishAt();
    String getReservationUrl();
    Integer getCost();
}
