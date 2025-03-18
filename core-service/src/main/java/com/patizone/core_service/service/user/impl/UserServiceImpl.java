package com.patizone.core_service.service.user.impl;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.mapper.UserMapper;
import com.patizone.core_service.repository.UserRepository;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.request.RequestUpdateUser;
import com.patizone.core_service.response.ResponsePage;
import com.patizone.core_service.response.ResponseUser;
import com.patizone.core_service.service.auth.intf.AuthenticatedUserService;
import com.patizone.core_service.service.user.intf.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }


  //TODO: burası user değil ResponseUser dönmeli, chatgptde son konuşma bunun hakkında.
  @Override
  public User save(RequestSignUp requestSignUp) {
    if (userRepository.findByEmail(requestSignUp.getEmail()).isPresent()) {
      throw new BusinessException("Email already taken %s".formatted(requestSignUp.getEmail()));
    }
    User user = userMapper.toUser(requestSignUp);
    user.setPassword(passwordEncoder.encode(requestSignUp.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public void deleteAccount(Long id) {
    if (!authenticatedUserService.isCurrentUser(id)) {
      throw new BusinessException("You are not allowed to delete this account");
    }
    if (!userRepository.existsById(id)) {
      throw new BusinessException("User with id %s not found".formatted(id));
    }
    userRepository.deleteById(id);
  }

  @Override
  public ResponseUser getById(Long id) {
    return userMapper.toResponseUser(userRepository.findById(id)
        .orElseThrow(() -> new BusinessException("User with id %s not found".formatted(id))));
  }

  @Override
  public ResponsePage<ResponseUser> getAll(int page, int size, String sortBy, Direction direction) {
    Pageable pageable;
    if (sortBy != null) {
      pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
    } else {
      pageable = PageRequest.of(page, size);
    }
    Page<User> userPage = userRepository.findAll(pageable);
    return new ResponsePage<ResponseUser>()
        .setContent(userMapper.toResponseUser(userPage.getContent()))
        .setPageNumber(userPage.getNumber())
        .setTotalElements(userPage.getTotalElements())
        .setLast(userPage.isLast())
        .setTotalPages(userPage.getTotalPages())
        .setPageSize(userPage.getSize());
  }

  @Override
  public List<ResponseUser> getAll() {
    return userMapper.toResponseUser(userRepository.findAll());
  }

  //password update should be different from general info update
  @Override
  public ResponseUser update(Long id, RequestUpdateUser requestUpdateUser) {
    User existingUser = userRepository.findById(id)
        .orElseThrow(() -> new BusinessException("User with id %s not found".formatted(id)));

    Long currentUserId = authenticatedUserService.getCurrentUserId();
    if (!currentUserId.equals(id)) {
      throw new BusinessException(
          "You are not the owner of profile, you cannot change profile info");
    }

    userMapper.updateUserFromRequest(requestUpdateUser, existingUser);
    return userMapper.toResponseUser(userRepository.save(existingUser));
  }

}
