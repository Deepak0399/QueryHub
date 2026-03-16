package com.practice.queryHub.services;

import com.practice.queryHub.dtos.AnswerDTO;
import com.practice.queryHub.model.Answer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface AnswerService {
    Answer createAnswer(AnswerDTO answerDTO);
    Optional<Answer> getAnswerById(UUID answerId);
    List<Answer> getAnswersByQuestionId(UUID questionId, int page, int size);
    void deleteAnswer(UUID answerId);

}
