package com.quiz.question.controller;

import com.quiz.question.client.ResultClient;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.service.SimpleQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final SimpleQuestionService questionService;
    private final ResultClient resultClient;

    public QuestionController(SimpleQuestionService questionService, ResultClient resultClient) {
        this.questionService = questionService;
        this.resultClient = resultClient;
    }

    @GetMapping("{sessionKey}/{questionKey}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable String sessionKey, @PathVariable Integer questionKey) {
        QuestionDTO question = questionService.getQuestion(sessionKey, questionKey);

    return question != null
            ? new ResponseEntity<>(questionService.getQuestion(sessionKey, questionKey), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/test-result-rabbit")
    public ResponseEntity<String> testResultRabbit() {
        String sessionKey = "test-session";
        Long questionId = 1L;
        int alternativeKey = 2;

        resultClient.sendValidationMessage(sessionKey, questionId, alternativeKey);

        return ResponseEntity.ok("Message sent to ResultService via RabbitMQ");
    }
}
