package com.patizone.core_service.integration.imgur.intf;

import com.patizone.core_service.config.FeignClientConfig;
import com.patizone.core_service.integration.imgur.response.ResponseImageUpload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "imgur-service", url = "https://api.imgur.com", configuration = FeignClientConfig.class)
public interface ImgurClient {

    @PostMapping(value = "/3/image", consumes = "multipart/form-data")
    ResponseImageUpload uploadFile(@RequestPart("image") MultipartFile image, @RequestHeader("Authorization") String auth);

}
