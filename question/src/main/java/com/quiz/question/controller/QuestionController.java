package com.quiz.question.controller;

import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.service.SimpleQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final SimpleQuestionService questionService;

    public QuestionController(SimpleQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/current")
    public ResponseEntity<QuestionDTO> getQuestion() {
        return new ResponseEntity<>(questionService.getQuestion(), HttpStatus.OK);
    }
}
