package com.quiz.quiz.controller;

import com.quiz.quiz.dto.questionDTO;
import com.quiz.quiz.dto.resultDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.conclusion.revealScoreDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;
import com.quiz.quiz.model.SessionStatus;
import com.quiz.quiz.service.SingleplayerQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    public ResponseEntity<questionDTO> getCurrentQuestion(@PathVariable String sessionKey) {
        questionDTO question = quizService.getCurrentQuestion(sessionKey);

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
    public ResponseEntity<resultDTO> postAnswer(@PathVariable String sessionKey, @RequestBody PostAnswerRequest answer) {
        resultDTO result = quizService.postAnswer(sessionKey, answer);

        return result != null
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("session/{sessionKey}/{username}/score")
    public ResponseEntity<revealScoreDTO> getScore(@PathVariable String sessionKey, @PathVariable String username) {
        revealScoreDTO score = quizService.getScore(sessionKey, username);

        return score != null
                ? new ResponseEntity<>(score, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("session/{sessionKey}/status")
    public ResponseEntity<SessionStatus> getStatus(@PathVariable String sessionKey) {
        return new ResponseEntity<>(quizService.getStatus(sessionKey), HttpStatus.OK);
    }
}
