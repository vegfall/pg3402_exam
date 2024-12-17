package com.quiz.ai.dto.request;

import com.quiz.ai.dto.MessageDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatGPTRequest {
    private final String model;
    private final List<MessageDTO> messages;

    public ChatGPTRequest(String model, String content) {
        this.model = model;
        this.messages = List.of(new MessageDTO("user", content));
    }
}
