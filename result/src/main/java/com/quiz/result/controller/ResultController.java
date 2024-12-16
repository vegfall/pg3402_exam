package com.quiz.result.controller;

import com.quiz.result.dto.ScoreDTO;
import com.quiz.result.service.SingleplayerResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result")
public class ResultController {
    private final SingleplayerResultService resultService;

    public ResultController(SingleplayerResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("{sessionKey}/{username}/score")
    public ResponseEntity<ScoreDTO> getScore(@PathVariable String sessionKey, @PathVariable String username) {
        ScoreDTO score = resultService.getScore(sessionKey, username);

        return score != null
                ? new ResponseEntity<>(score, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}