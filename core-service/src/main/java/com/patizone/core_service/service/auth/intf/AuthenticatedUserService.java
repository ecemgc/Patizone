package com.patizone.core_service.service.auth.intf;


import com.patizone.core_service.entity.User;

public interface AuthenticatedUserService {
    User getCurrentUser();
    Long getCurrentUserId();
    boolean isCurrentUser(Long id);
}
