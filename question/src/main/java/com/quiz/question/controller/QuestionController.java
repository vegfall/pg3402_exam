package com.quiz.question.controller;

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

    public QuestionController(SimpleQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("{sessionKey}/{questionKey}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable String sessionKey, @PathVariable Integer questionKey) {
        QuestionDTO question = questionService.getQuestion(sessionKey, questionKey);

    return question != null
            ? new ResponseEntity<>(questionService.getQuestion(sessionKey, questionKey), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
