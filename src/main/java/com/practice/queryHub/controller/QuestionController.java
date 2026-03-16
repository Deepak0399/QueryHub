package com.practice.queryHub.controller;

import com.practice.queryHub.dtos.QuestionDTO;
import com.practice.queryHub.model.Question;
import com.practice.queryHub.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionDTO questionDTO) {
        Question question = questionService.createQuestion(questionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(@RequestParam int page, @RequestParam int size) {
        List<Question> questions = questionService.getQuestions(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable UUID questionId) {
        Optional<Question> question = questionService.getQuestionById(questionId);
        return question.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
