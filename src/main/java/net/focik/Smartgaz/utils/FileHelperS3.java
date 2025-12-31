package net.focik.Smartgaz.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class FileHelperS3 {

    private final S3Client s3Client;
    private final String homeUrl;
    private final String bucketName;

    public FileHelperS3(@Autowired S3Client s3Client, @Value("${smartgaz.url}") String homeUrl, @Value("${aws.bucket.name}") String bucketName) {
        this.homeUrl = homeUrl;
        this.bucketName = bucketName;
        this.s3Client = s3Client;
    }

    public String saveInBucket(File file) {
        String originalFileName = file.getName();
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i);
        }
        String uniqueFileName = UUID.randomUUID() + extension;

        try (InputStream inputStream = new FileInputStream(file)) {
            log.debug("Uploading file {} to S3 bucket {}", uniqueFileName, bucketName);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType("application/octet-stream") // General content type
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.length()));

            String publicS3Url = homeUrl + uniqueFileName;
            log.debug("S3 saved file: {}", publicS3Url);
            return publicS3Url;
        } catch (IOException e) {
            log.error("Error uploading file to S3", e);
            return null;
        }
    }
}
