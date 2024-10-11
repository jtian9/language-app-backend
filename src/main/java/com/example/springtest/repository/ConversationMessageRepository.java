package com.example.springtest.repository;

import com.example.springtest.entity.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationMessageRepository extends JpaRepository<ConversationMessage, Long> {
}
