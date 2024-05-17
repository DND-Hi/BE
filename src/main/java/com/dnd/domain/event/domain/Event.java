package com.dnd.domain.event.domain;

import com.dnd.domain.common.model.BaseTimeEntity;
import com.dnd.domain.member.domain.Member;
import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String title;

    private String description;

    private String host;

    @Enumerated(EnumType.STRING)
    private HostType hostType;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageUploadStatus uploadStatus;

    private Double longitude;

    private Double latitude;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String reservationUrl;

    private Integer cost;

    private int bookmarkCount;

    @Builder(access = AccessLevel.PRIVATE)
    public Event(String title,
                 String description,
                 String host,
                 HostType hostType,
                 Double longitude,
                 Double latitude,
                 String imageUrl,
                 ImageUploadStatus uploadStatus,
                 LocalDateTime startAt,
                 LocalDateTime finishAt,
                 String reservationUrl,
                 Integer cost,
                 int bookmarkCount,
                 Member member) {
        this.title = title;
        this.description = description;
        this.host = host;
        this.hostType = hostType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageUrl = imageUrl;
        this.uploadStatus = uploadStatus;
        this.startAt = startAt;
        this.finishAt = finishAt;
        this.member = member;
        this.reservationUrl = reservationUrl;
        this.cost = cost;
        this.bookmarkCount = bookmarkCount;
    }

    public static Event createEvent(
            String title,
            String description,
            String host,
            HostType hostType,
            Double longitude,
            Double latitude,
            LocalDateTime startAt,
            LocalDateTime finishAt,
            String reservationUrl,
            Integer cost,
            String imageUrl,
            Member member) {

        return Event.builder()
                .title(title)
                .description(description)
                .host(host)
                .hostType(hostType)
                .uploadStatus(ImageUploadStatus.NONE)
                .longitude(longitude)
                .latitude(latitude)
                .startAt(startAt)
                .finishAt(finishAt)
                .imageUrl(imageUrl)
                .member(member)
                .uploadStatus(ImageUploadStatus.NONE)
                .reservationUrl(reservationUrl)
                .cost(cost)
                .bookmarkCount(0)
                .build();
    }

    public void updateImageUploadStatusComplete(String imageUrl) {
        if (this.uploadStatus != ImageUploadStatus.PENDING) {
            throw new CustomException(ErrorCode.EVENT_UPLOAD_STATUS_IS_NOT_PENDING);
        }
        this.uploadStatus = ImageUploadStatus.COMPLETE;
        this.imageUrl = imageUrl;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateImageUploadStatusPending() {
        if (this.uploadStatus != ImageUploadStatus.NONE) {
            throw new CustomException(ErrorCode.EVENT_UPLOAD_STATUS_IS_NOT_NONE);
        }
        this.uploadStatus = ImageUploadStatus.PENDING;
    }

    public void updateEvent(String title, String description, String host, Double longitude, Double latitude,
        LocalDateTime startAt, LocalDateTime finishAt, String reservationUrl, Integer cost, String imageUrl) {
        this.title = title;
        this.description = description;
        this.host = host;
        this.longitude = longitude;
        this.latitude = latitude;
        this.startAt = startAt;
        this.finishAt = finishAt;
        this.reservationUrl = reservationUrl;
        this.cost = cost;
        this.imageUrl = imageUrl;
    }

    public void increaseBookmarkCount() {
        this.bookmarkCount ++;
    }

    public void decreaseBookmarkCount() {
        if (this.bookmarkCount == 0) {
            return ;
        }

        this.bookmarkCount --;
    }
}
