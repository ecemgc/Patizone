package com.patizone.core_service.messaging;


import com.patizone.core_service.dto.UserMessageDTO;
import com.patizone.core_service.security.CustomUserDetailService;
import com.patizone.core_service.security.JwtUtils;
import com.patizone.core_service.service.message.intf.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate;
  private final MessageService messageService;
  private final CustomUserDetailService customUserDetailService;

  @MessageMapping("/chat")
  public void sendMessage(@Payload UserMessageDTO message) {
    setAuth(message.getSenderEmail());
    messagingTemplate.convertAndSendToUser(
            message.getReceiverEmail(), "/queue/messages", message);
    messageService.save(message);
  }

  private void setAuth(String senderEmail) {
    var userDetails = customUserDetailService.loadUserByUsername(senderEmail);
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            Collections.emptyList()
    );
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }
}
