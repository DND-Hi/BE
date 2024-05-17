package com.dnd.domain.image.handler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.domain.image.dto.response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NcpStorageHandler implements ImageStorageHandler{
    private static final String ROOT_DIRNAME = "dev";

    private final AmazonS3 amazonS3;

    private static final String BUCKET = "more-images";

    @Override
    public ImageResponse upload(File file) {
        String key = generateRandomFileName(file);
        String path = putImage(file, key);
        removeFile(file);

        return new ImageResponse(key, path);
    }

    private void removeFile(File file) {
        file.delete();
    }

    private String generateRandomFileName(File file) {
        return ROOT_DIRNAME + "/" + UUID.randomUUID().toString().substring(0, 8) + "-" + file.getName();
    }

    private String putImage(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(BUCKET, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return getImagePath(BUCKET, fileName);
    }

    private String getImagePath(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
