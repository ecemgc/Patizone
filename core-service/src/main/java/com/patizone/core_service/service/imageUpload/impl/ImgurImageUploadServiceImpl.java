package com.patizone.core_service.service.imageUpload.impl;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.exceptions.NotFoundException;
import com.patizone.core_service.integration.imgur.intf.ImgurClient;
import com.patizone.core_service.integration.imgur.response.ResponseImageUpload;
import com.patizone.core_service.request.RequestUpdateUser;
import com.patizone.core_service.response.ResponseUser;
import com.patizone.core_service.service.imageUpload.intf.ImageUploadService;
import com.patizone.core_service.service.user.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImgurImageUploadServiceImpl implements ImageUploadService {

    private final ImgurClient imgurClient;
    private final UserService userService;

    @Value("${imgur.clientId}")
    private String clientId;

    @Override
    public ResponseImageUpload upload(MultipartFile image) {
        return imgurClient.uploadFile(image, "Client-ID " + clientId);
    }

    @Override
    public ResponseUser uploadUserImage(Long userId, MultipartFile image) {
        User user = userService.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        var response = upload(image);
        var requestUser = new RequestUpdateUser().setImageUrl(response.getData().getLink());
        return userService.update(user.getId(), requestUser);
    }


}
