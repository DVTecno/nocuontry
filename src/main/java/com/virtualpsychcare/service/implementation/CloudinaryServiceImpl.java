package com.virtualpsychcare.service.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImpl {
    Cloudinary cloudinary;
    private Map<String, String> valueMap = new HashMap<>();

    public CloudinaryServiceImpl() {
        valueMap.put("cloud_name", "dcq6ecx2k");
        valueMap.put("api_key", "897877274521811");
        valueMap.put("api_secret", "A3rl6D3E363wgIqxxhAsUirFUKc");
        valueMap.put("secure", "true");
        cloudinary = new Cloudinary(valueMap);
    }

    public Map<String, Object> uploadImage(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Map<String, Object> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if (!file.delete()) {
            throw new IOException("Failed to delete temporary file " + file.getName());
        }
        return result;
    }

    public Map<String, Object> delete(String id) throws IOException {
        Map<String, Object> result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();

        return file;
    }
}
