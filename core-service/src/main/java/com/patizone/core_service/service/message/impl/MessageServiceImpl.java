package com.patizone.core_service.service.message.impl;

import com.patizone.core_service.dto.UserMessageDTO;
import com.patizone.core_service.entity.Message;
import com.patizone.core_service.enums.MessageType;
import com.patizone.core_service.exceptions.NotFoundException;
import com.patizone.core_service.mapper.MessageMapper;
import com.patizone.core_service.repository.MessageRepository;
import com.patizone.core_service.service.auth.intf.AuthenticatedUserService;
import com.patizone.core_service.service.message.intf.MessageService;
import com.patizone.core_service.service.user.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final AuthenticatedUserService authenticatedUserService;
    private final UserService userService;

    @Override
    public void save(UserMessageDTO dto) {
        if(!MessageType.MESSAGE.equals(dto.getMessageType())) {
            return;
        }
        var message = new Message();
        message.setMessage(dto.getContent());
        message.setSender(authenticatedUserService.getCurrentUser());
        message.setReceiver(userService.findByEmail(dto.getReceiverEmail()).orElseThrow(() -> new NotFoundException("User not found")));
        messageRepository.save(message);
    }

    @Override
    public List<UserMessageDTO> getConversation(Long senderId) {
        Long receiverId = authenticatedUserService.getCurrentUserId();
        var messages = messageRepository.getConversationOfTwoUser(receiverId, senderId);
        return messageMapper.toUserMessageDTO(messages);
    }
}
