package com.quiz.quiz.controller;

import com.quiz.quiz.dto.QuestionDTO;
import com.quiz.quiz.dto.ResultDTO;
import com.quiz.quiz.dto.SessionDTO;
import com.quiz.quiz.dto.SessionScoreDTO;
import com.quiz.quiz.dto.conclusion.revealScoreDTO;
import com.quiz.quiz.dto.request.CreateSessionRequest;
import com.quiz.quiz.dto.request.LoadSessionRequest;
import com.quiz.quiz.dto.request.PostAnswerRequest;
import com.quiz.quiz.model.SessionStatus;
import com.quiz.quiz.service.SingleplayerQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("session/{sessionKey}/load")
    public ResponseEntity<SessionDTO> loadPreviousSession(@PathVariable String sessionKey, @RequestBody LoadSessionRequest request) {
        SessionDTO session = quizService.loadPreviousSession(sessionKey, request);

        return session != null
                ? new ResponseEntity<>(session, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("session/{sessionKey}/start")
    public ResponseEntity<HttpStatus> startSession(@PathVariable String sessionKey) {
        quizService.startSession(sessionKey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("session/{sessionKey}/info")
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

    @GetMapping("session/{sessionKey}/scores")
    public ResponseEntity<List<SessionScoreDTO>> getScores(@PathVariable String sessionKey) {
        List<SessionScoreDTO> sessionScores = quizService.getScores(sessionKey);

        return sessionScores.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(sessionScores, HttpStatus.OK);
    }

    @GetMapping("session/get-sessions")
    public ResponseEntity<List<SessionDTO>> getSessions() {
        List<SessionDTO> sessions = quizService.getSessions();

        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
}
