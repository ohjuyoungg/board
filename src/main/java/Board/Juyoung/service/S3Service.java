package Board.Juyoung.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.region.static}")
    private String region;


    public String uploadImage(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("File name is missing.");
        }

        String extension = "";
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex != -1) {
            extension = originalFilename.substring(lastDotIndex);
        }
        String key = UUID.randomUUID() + extension;
        URL url;

        try (InputStream imageInputStream = image.getInputStream()) {
            url = s3Template.upload(
                bucket,
                key,
                imageInputStream,
                ObjectMetadata.builder()
                    .contentType(image.getContentType())
                    .build()
            ).getURL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return url.toString();
    }

    public void deleteImage(String url) {
        String key;
        try {
            key = new URI(url)
                .toURL()
                .getPath()
                .substring(1);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("S3 Error", e);
        }
        s3Template.deleteObject(bucket, key);
    }
}
