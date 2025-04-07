package com.patizone.core_service.controller;

import com.patizone.core_service.response.ResponseUser;
import com.patizone.core_service.service.imageUpload.intf.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/upload")
@RequiredArgsConstructor
@RestController
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping(path = "/user-pp/{userId}", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.OK)
    public ResponseUser uploadUserImage(@PathVariable Long userId, @RequestPart("image") MultipartFile image) {
        return imageUploadService.uploadUserImage(userId, image);
    }
}
