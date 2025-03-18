package com.patizone.core_service.service.imageUpload.intf;

import com.patizone.core_service.integration.imgur.response.ResponseImageUpload;
import com.patizone.core_service.response.ResponseUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    ResponseImageUpload upload(MultipartFile image);

    ResponseUser uploadUserImage(Long userId, MultipartFile image);
}
