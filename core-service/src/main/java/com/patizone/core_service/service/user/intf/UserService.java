package com.patizone.core_service.service.user.intf;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.request.RequestSignUp;
import com.patizone.core_service.request.RequestUpdateUser;
import com.patizone.core_service.response.ResponsePage;
import com.patizone.core_service.response.ResponseUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;


public interface UserService {

  Optional<User> findByEmail(String email);

  User save(RequestSignUp requestSignUp);

  void deleteAccount(Long id);

  ResponseUser getById(Long id);

  ResponsePage<ResponseUser> getAll(int page, int size, String sortBy, Sort.Direction direction);

  List<ResponseUser> getAll();

  void update(Long id, RequestUpdateUser requestUpdateUser);
}
