package com.patizone.core_service.controller;

import com.patizone.core_service.dto.UserMessageDTO;
import com.patizone.core_service.entity.Message;
import com.patizone.core_service.exceptions.NotFoundException;
import com.patizone.core_service.service.message.intf.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{senderId}")
    public List<UserMessageDTO> getConversation(@PathVariable Long senderId) {
       return messageService.getConversation(senderId);
    }
}
