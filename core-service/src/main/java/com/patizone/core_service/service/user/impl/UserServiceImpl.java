package com.patizone.core_service.service.user.impl;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.mapper.UserMapper;
import com.patizone.core_service.repository.UserRepository;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.response.ResponseAuth;
import com.patizone.core_service.security.JwtUtils;
import com.patizone.core_service.service.auth.intf.AuthenticatedUserService;
import com.patizone.core_service.service.user.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticatedUserService authenticatedUserService;
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(RequestSignUp requestSignUp) {
        if(userRepository.findByEmail(requestSignUp.getEmail()).isPresent()){
            throw new BusinessException("Email already taken %s".formatted(requestSignUp.getEmail()));
        }
        User user = userMapper.toUser(requestSignUp);
        user.setPassword(passwordEncoder.encode(requestSignUp.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteAccount(Long id) {
        if(!authenticatedUserService.isCurrentUser(id)){
            throw new BusinessException("You are not allowed to delete this account");
        }
        if(!userRepository.existsById(id)){
            throw new BusinessException("User with id %s not found".formatted(id));
        }
        userRepository.deleteById(id);
    }

}
