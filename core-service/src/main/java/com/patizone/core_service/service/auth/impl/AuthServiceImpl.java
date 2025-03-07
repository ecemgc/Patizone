package com.patizone.core_service.service.auth.impl;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.mapper.UserMapper;
import com.patizone.core_service.repository.UserRepository;
import com.patizone.core_service.request.RequestLogin;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.response.ResponseAuth;
import com.patizone.core_service.security.JwtUtils;
import com.patizone.core_service.service.auth.intf.AuthService;
import com.patizone.core_service.service.user.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public ResponseAuth login(RequestLogin requestLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestLogin.getEmail(), requestLogin.getPassword()));
        User user = (User) authentication.getPrincipal();
        return new ResponseAuth()
                .setToken(jwtUtils.generateToken(user.getEmail()))
                .setUser(userMapper.toResponseUser(user));
    }

    @Override
    public ResponseAuth signUp(RequestSignUp requestSignUp){
        User user = userService.save(requestSignUp);
        String token = jwtUtils.generateToken(user.getEmail());
        return new ResponseAuth()
                .setToken(token)
                .setUser(userMapper.toResponseUser(user));
    }
}
