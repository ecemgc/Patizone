package com.patizone.core_service.controller;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.messaging.SessionRegistryService;
import com.patizone.core_service.request.RequestGetUsers;
import com.patizone.core_service.response.ResponseUser;
import com.patizone.core_service.service.user.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  
    private final UserService userService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteAccount(@PathVariable  Long id) {
        userService.deleteAccount(id);
        return "Account deleted successfully";
    }

    @GetMapping
    public List<ResponseUser> getAll() {
        return userService.getAll();
    }

}
