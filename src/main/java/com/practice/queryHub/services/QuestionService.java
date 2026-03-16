package com.practice.queryHub.services;

import com.practice.queryHub.dtos.QuestionDTO;
import com.practice.queryHub.model.Question;
import com.practice.queryHub.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public interface QuestionService {
    Question createQuestion(QuestionDTO questionDTO);
    void deleteQuestion(UUID questionId);
    List<Question> getQuestions(int page, int size);
    Optional<Question> getQuestionById(UUID questionId);
    List<Question> getQuestionByTag(Set<UUID> tagId, int page, int size);
}
