package com.patizone.core_service.repository;

import com.patizone.core_service.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
            select m from Message m where
             (m.receiver.id = ?1 and m.sender.id = ?2) or
             (m.receiver.id = ?2 and m.sender.id = ?1)
             order by m.createdAt asc
            """)
    List<Message> getConversationOfTwoUser(Long receiverId, Long senderId);
}
