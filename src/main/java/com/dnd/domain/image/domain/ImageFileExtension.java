package com.dnd.domain.image.domain;

import java.util.Arrays;

import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFileExtension {
    JPEG("jpeg"),
    JPG("jpg"),
    PNG("png"),
    ;

    private final String uploadExtension;

    public static ImageFileExtension of(String uploadExtension) {
        return Arrays.stream(values())
                .filter(
                        imageFileExtension ->
                                imageFileExtension.uploadExtension.equals(uploadExtension))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.IMAGE_FILE_EXTENSION_NOT_FOUND));
    }
}
