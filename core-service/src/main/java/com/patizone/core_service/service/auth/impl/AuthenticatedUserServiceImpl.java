package com.patizone.core_service.service.auth.impl;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.UnauthorizedException;
import com.patizone.core_service.service.auth.intf.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymous")){
            throw new UnauthorizedException("User is not authenticated");
        }
        return (User) authentication.getPrincipal();
    }
    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @Override
    public boolean isCurrentUser(Long id) {
        return getCurrentUser().getId().equals(id);
    }
}
