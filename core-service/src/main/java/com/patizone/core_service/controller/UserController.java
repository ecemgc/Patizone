package com.patizone.core_service.controller;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.messaging.SessionRegistryService;
import com.patizone.core_service.service.user.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SessionRegistryService sessionRegistryService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteAccount(@PathVariable  Long id) {
        userService.deleteAccount(id);
        return "Account deleted successfully";
    }

}
