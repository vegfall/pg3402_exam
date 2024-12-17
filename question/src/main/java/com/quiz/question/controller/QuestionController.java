package com.quiz.question.controller;

import com.quiz.question.dto.QuestionDTO;
import com.quiz.question.dto.ResultDTO;
import com.quiz.question.dto.SessionScoreDTO;
import com.quiz.question.dto.conclusion.RevealScoreDTO;
import com.quiz.question.dto.request.NewSessionRequest;
import com.quiz.question.dto.request.PostAnswerRequest;
import com.quiz.question.dto.response.AIChatResponse;
import com.quiz.question.event.AIEventHandler;
import com.quiz.question.service.SingleplayerQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final SingleplayerQuestionService questionService;
    private final AIEventHandler aiEventHandler;

    public QuestionController(SingleplayerQuestionService questionService, AIEventHandler aiEventHandler) {
        this.questionService = questionService;
        this.aiEventHandler = aiEventHandler;
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

    @GetMapping("{sessionKey}/{username}/score")
    public ResponseEntity<RevealScoreDTO> getScore(@PathVariable String sessionKey, @PathVariable String username) {
        RevealScoreDTO score = questionService.getScore(sessionKey, username);

        return score != null
                ? new ResponseEntity<>(score, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{sessionKey}/{currentQuestionKey}/check-more")
    public ResponseEntity<Boolean> checkMoreQuestions(@PathVariable String sessionKey, @PathVariable Integer currentQuestionKey) {
        return new ResponseEntity<>(questionService.checkMoreQuestions(sessionKey, currentQuestionKey), HttpStatus.OK);
    }

    @GetMapping("{sessionKey}/scores")
    public ResponseEntity<List<SessionScoreDTO>> getScores(@PathVariable String sessionKey) {
        List<SessionScoreDTO> sessionScores = questionService.getScores(sessionKey);

        return sessionScores.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(sessionScores, HttpStatus.OK);
    }

    @PostMapping("post-session")
    public ResponseEntity<HttpStatus> postSession(@RequestBody NewSessionRequest request) {
        questionService.postSession(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
