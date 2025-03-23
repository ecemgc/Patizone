package com.patizone.core_service.mapper;

import com.patizone.core_service.dto.UserMessageDTO;
import com.patizone.core_service.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "sender.email", target = "senderEmail")
    @Mapping(source = "receiver.email", target = "receiverEmail")
    @Mapping(source = "message", target = "content")
    @Mapping(source = "createdAt", target = "timestamp")
    UserMessageDTO toUserMessageDTO(Message message);

    List<UserMessageDTO> toUserMessageDTO(List<Message> messages);
}