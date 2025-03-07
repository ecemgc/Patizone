package com.patizone.core_service.messaging;


import com.patizone.core_service.dto.UserMessageDTO;
import com.patizone.core_service.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

  private final MessageService messageService;
  private final JwtUtils jwtUtils;

  @MessageMapping("/chat")
  public void sendMessage(@Header("Authorization") String authHeader,
      @Payload UserMessageDTO message) {
    String senderEmail = jwtUtils.extractUsername(authHeader.substring(7));
    messageService.sendMessage(senderEmail, message.getReceiverEmail(), message.getContent());
  }
}
