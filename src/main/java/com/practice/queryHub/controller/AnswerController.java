package com.practice.queryHub.controller;

import com.practice.queryHub.dtos.AnswerDTO;
import com.practice.queryHub.model.Answer;
import com.practice.queryHub.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/answers")
public class AnswerController {
    private final AnswerService answerService;
    
    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody AnswerDTO answerDTO) {
        Answer answer = answerService.createAnswer(answerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(answer);
    }
    
    @GetMapping("/{answerId}")
    public ResponseEntity<Answer> getAnswerByID(@PathVariable UUID answerId) {
        Optional<Answer> answer = answerService.getAnswerById(answerId);
        return answer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Answer>> getAnswersByQuestionId(@PathVariable UUID questionId, @RequestParam int page, @RequestParam int size) {
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(answers);
    }
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable UUID answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
    
}
