package com.quiz.quiz.controller;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.ResultDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;
import com.quiz.quiz.service.SingleplayerQuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final SingleplayerQuizService quizService;

    public QuizController(SingleplayerQuizService quizService) {
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
        SessionDTO session = quizService.getSession(sessionKey);

        return session != null
                ? new ResponseEntity<>(session, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("session/{sessionKey}/current-question")
    public ResponseEntity<QuestionDTO> getCurrentQuestion(@PathVariable String sessionKey) {
        QuestionDTO question = quizService.getCurrentQuestion(sessionKey);

        return question != null
                ? new ResponseEntity<>(question, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("session/{sessionKey}/next-question")
    public ResponseEntity<HttpStatus> putNextQuestion(@PathVariable String sessionKey) {
        quizService.putNextQuestion(sessionKey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("session/{sessionKey}/post-answer")
    public ResponseEntity<ResultDTO> postAnswer(@PathVariable String sessionKey, @RequestBody PostAnswerRequest answer) {
        ResultDTO result = quizService.postAnswer(sessionKey, answer);

        return result != null
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
