package com.patizone.core_service.mapper;


import com.patizone.core_service.entity.User;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.request.RequestUpdateUser;
import com.patizone.core_service.response.ResponseUser;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

  ResponseUser toResponseUser(User user);

  User toUser(RequestSignUp requestSignUp);

  List<ResponseUser> toResponseUser(List<User> user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUserFromRequest(RequestUpdateUser requestUpdateUser, @MappingTarget User user);
}
