package org.pplan.service.image;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${image.upload-dir}")
    private String uploadDir;

    private static final String PATH_SEPARATOR = File.separator;

    public String[] processImage(MultipartFile image) throws IOException {
        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + fileExtension;
        File dest = new File(uploadDir + PATH_SEPARATOR + fileName);
        image.transferTo(dest);

        File thumbnailFile = new File(uploadDir + PATH_SEPARATOR + "thumbnail_" + fileName);
        Thumbnails.of(dest).size(100, 100).toFile(thumbnailFile);

        return new String[]{fileName, "thumbnail_" + fileName};
    }
}