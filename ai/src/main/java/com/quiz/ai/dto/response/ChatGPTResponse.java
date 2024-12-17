package com.quiz.ai.dto.response;

import com.quiz.ai.dto.ChoiceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTResponse {
    private List<ChoiceDTO> choices;
}
