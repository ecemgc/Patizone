package com.patizone.core_service.service.user.intf;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.response.ResponseAuth;

import java.util.Optional;


public interface UserService {
    Optional<User> findByEmail(String email);
    User save(RequestSignUp requestSignUp);
    void deleteAccount(Long id);
}
