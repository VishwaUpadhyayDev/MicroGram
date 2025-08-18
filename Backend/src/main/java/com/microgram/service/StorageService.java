package com.microgram.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${app.storage.supabase.bucket}")
    private String bucketName;

    @Value("${app.storage.supabase.url}")
    private String supabaseUrl;

    @Value("${app.storage.supabase.service-key}")
    private String serviceKey;

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = "posts/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(serviceKey);
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return fileName;
            }
        });
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        String uploadUrl = supabaseUrl + "/object/" + bucketName + "/" + fileName;
        
        ResponseEntity<String> response = restTemplate.exchange(
            uploadUrl, 
            HttpMethod.POST, 
            requestEntity, 
            String.class
        );
        
        if (response.getStatusCode().is2xxSuccessful()) {
            return supabaseUrl.replace("/storage/v1", "") + "/storage/v1/object/public/" + bucketName + "/" + fileName;
        } else {
            throw new IOException("Failed to upload image: " + response.getBody());
        }
    }
}