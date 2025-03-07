package com.patizone.core_service.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMessageDTO {

  private String senderEmail;
  private String receiverEmail;
  private String content;
  private LocalDateTime timestamp;
}
