package com.quiz.question.controller;

import com.quiz.question.client.ResultClient;
import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.request.PostAnswerRequest;
import com.quiz.question.service.SingleplayerQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final SingleplayerQuestionService questionService;

    public QuestionController(SingleplayerQuestionService questionService, ResultClient resultClient) {
        this.questionService = questionService;
    }

    @GetMapping("{sessionKey}/{questionKey}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable String sessionKey, @PathVariable Integer questionKey) {
        QuestionDTO question = questionService.getQuestion(sessionKey, questionKey);

    return question != null
            ? new ResponseEntity<>(question, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{sessionKey}/{questionKey}/post-answer")
    public ResponseEntity<ResultDTO> postAnswer(@PathVariable String sessionKey, @PathVariable Integer questionKey, @RequestBody PostAnswerRequest answer) {
        ResultDTO result = questionService.postAnswer(sessionKey, questionKey, answer);

        return result != null
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.OK);
    }
}
