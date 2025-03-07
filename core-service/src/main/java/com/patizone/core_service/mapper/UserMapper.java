package com.patizone.core_service.mapper;


import com.patizone.core_service.entity.User;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.response.ResponseUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUser toResponseUser(User user);
    User toUser(RequestSignUp requestSignUp);
}
