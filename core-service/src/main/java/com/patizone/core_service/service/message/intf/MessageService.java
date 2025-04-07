package com.patizone.core_service.service.message.intf;

import com.patizone.core_service.dto.UserMessageDTO;

import java.util.List;

public interface MessageService {
    void save(UserMessageDTO dto);
    List<UserMessageDTO> getConversation(Long userId);
}
