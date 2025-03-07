package com.patizone.core_service.messaging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class SessionRegistryService {

  private final Map<String, String> userSessionMap = new ConcurrentHashMap<>(); // email -> sessionId
  private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>(); // sessionId -> email

  public void registerUser(String email, String sessionId) {
    userSessionMap.put(email, sessionId);
    sessionUserMap.put(sessionId, email);
  }

  public String getSessionId(String email) {
    return userSessionMap.get(email);
  }

  public String findUserEmailBySessionId(String sessionId) {
    return sessionUserMap.get(sessionId);
  }

  public void removeUser(String email) {
    String sessionId = userSessionMap.remove(email);
    if (sessionId != null) {
      sessionUserMap.remove(sessionId);
    }
  }
}
