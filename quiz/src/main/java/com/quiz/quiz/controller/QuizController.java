package com.quiz.quiz.controller;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.service.SimpleQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final SimpleQuizService quizService;

    public QuizController(SimpleQuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("session/create")
    public ResponseEntity<SessionDTO> postNewSession(@RequestBody CreateSessionRequest request) {
        SessionDTO session = quizService.postNewSession(request);

        return session != null
                ? new ResponseEntity<>(session, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("session/info/{sessionKey}")
    public ResponseEntity<SessionDTO> getSessionInfo(@PathVariable String sessionKey) {
        log.info("HEI");

        return null;
    }

    @GetMapping("{sessionKey}/current-question")
    public ResponseEntity<QuestionDTO> getCurrentQuestion(@PathVariable String sessionKey) {
        QuestionDTO question = quizService.getCurrentQuestion(sessionKey);

        return question != null
                ? new ResponseEntity<>(question, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{sessionKey}/next-question")
    public ResponseEntity<HttpStatus> putNextQuestion(@PathVariable String sessionKey) {
        quizService.putNextQuestion(sessionKey);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
