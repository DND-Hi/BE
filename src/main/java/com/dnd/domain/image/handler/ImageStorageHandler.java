package com.dnd.domain.image.handler;

import com.dnd.domain.image.dto.response.ImageResponse;

import java.io.File;

public interface ImageStorageHandler {

    ImageResponse upload(File file);
}
