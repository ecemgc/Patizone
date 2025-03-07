package com.patizone.core_service.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final SimpMessagingTemplate messagingTemplate;
  private final SessionRegistryService sessionRegistryService;

  public void sendMessage(String senderEmail, String receiverEmail, String message) {
    messagingTemplate.convertAndSendToUser(receiverEmail, "/queue/messages", message);
  }
}

