package com.patizone.core_service.response;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseAuth {
    private String token;
    private ResponseUser user;
}
