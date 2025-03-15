package com.patizone.core_service.messaging;


import com.patizone.core_service.dto.UserMessageDTO;
import com.patizone.core_service.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat")
  public void sendMessage(@Payload UserMessageDTO message, Principal principal) {
    String senderEmail = principal.getName();
    message.setSenderEmail(senderEmail);
    messagingTemplate.convertAndSendToUser(
            message.getReceiverEmail(), "/queue/messages", message);
  }
}
