package com.quiz.quiz.controller;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.service.SimpleQuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QuizController {
    private final SimpleQuizService quizService;

    public QuizController(SimpleQuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/test")
    public ResponseEntity<QuestionDTO> test() {
        return new ResponseEntity<>(quizService.test(), HttpStatus.OK);
    }
}
