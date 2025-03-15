package com.patizone.core_service.messaging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SessionRegistryService {

  private final Map<String, String> userSessionMap = new ConcurrentHashMap<>(); // email -> sessionId

  public void registerUser(String email, String sessionId) {
    userSessionMap.put(email, sessionId);
  }


  public void removeUser(String email) {
    if(!StringUtils.hasText(email)) {
      return;
    }
    userSessionMap.remove(email);
  }
}
