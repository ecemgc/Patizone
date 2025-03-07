package com.patizone.core_service.service.auth.intf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.request.RequestLogin;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.response.ResponseAuth;

public interface AuthService {

    ResponseAuth login(RequestLogin requestLogin) throws JsonProcessingException;
    ResponseAuth signUp(RequestSignUp requestSignUp);
}
