package com.patizone.core_service.request;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RequestLogin {
    private String email;
    private String password;
}
