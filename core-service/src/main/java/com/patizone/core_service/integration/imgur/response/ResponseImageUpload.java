package com.patizone.core_service.integration.imgur.response;

import com.patizone.core_service.integration.imgur.dto.ImgurDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseImageUpload {
    private int status;
    private boolean success;
    private ImgurDTO data;
}