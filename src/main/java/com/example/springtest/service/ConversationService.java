package com.example.springtest.service;

import com.example.springtest.entity.Conversation;
import com.example.springtest.entity.ConversationMessage;
import com.example.springtest.entity.Learner;
import com.example.springtest.repository.ConversationMessageRepository;
import com.example.springtest.repository.ConversationRepository;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationService {
    ConversationRepository conversationRepository;
    ConversationMessageRepository conversationMessageRepository;
    LearnerService learnerService;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, ConversationMessageRepository conversationMessageRepository, LearnerService learnerService) {
        this.conversationRepository = conversationRepository;
        this.conversationMessageRepository = conversationMessageRepository;
        this.learnerService = learnerService;
    }

    public Conversation getConversationByID(Long id) {
        return conversationRepository.findById(id).orElse(null);
    }

    public Long newConversation(Conversation conversation) {
        Conversation savedConversation = conversationRepository.save(conversation);
        return savedConversation.getId();
    }

    public Long newConversation(String userMessage, String aiMessage, String systemMessage, String username) {
        Learner learner = learnerService.getOrAddLearner(username);
        Conversation conversation = new Conversation(systemMessage, learner);

        ConversationMessage userConversationMessage = new ConversationMessage(conversation, "USER", userMessage);
        ConversationMessage aiConversationMessage = new ConversationMessage(conversation, "AI", aiMessage);

        conversation.addConversationMessage(userConversationMessage);
        conversation.addConversationMessage(aiConversationMessage);

        Conversation savedConversation = conversationRepository.save(conversation);
        return savedConversation.getId();
    }

    public void updateConversation(Long ConversationID, String userMessage, String aiMessage) {
        Optional<Conversation> optionalConversation = conversationRepository.findById(ConversationID);

        if (optionalConversation.isPresent()) {
            Conversation conversation = optionalConversation.get();

            ConversationMessage userConversationMessage = new ConversationMessage(conversation, "USER", userMessage);
            ConversationMessage aiConversationMessage = new ConversationMessage(conversation, "AI", aiMessage);

            conversationMessageRepository.save(userConversationMessage);
            conversationMessageRepository.save(aiConversationMessage);

            conversation.addConversationMessage(userConversationMessage);
            conversation.addConversationMessage(aiConversationMessage);

            conversationRepository.save(conversation);
        } else {
            throw new RuntimeException("conversation not found");
        }

    }



}
